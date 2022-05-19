package com.baris.nasaproject.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.baris.nasaproject.R
import com.baris.nasaproject.common.Constants
import com.baris.nasaproject.data.remote.nasa.dto.Photo
import com.baris.nasaproject.databinding.FragmentViewPagerBinding
import com.baris.nasaproject.domain.model.Nasa
import com.baris.nasaproject.domain.model.State
import com.baris.nasaproject.ui.adapter.PhotoListAdapter
import com.baris.nasaproject.ui.scroll.RoverScrollListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var nasaState: State<Nasa>
    private val cameras = ArrayList<String>()
    private var photoList = ArrayList<Photo>()
    private lateinit var roverName: String
    private lateinit var photoAdapter: PhotoListAdapter

    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())

        arguments?.takeIf { it.containsKey("ROVER") }?.apply {
            roverName = context?.getString(getInt("ROVER")).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNasaState()
        observeCameraState()

        photoAdapter = PhotoListAdapter(photoList, binding.root)

        mLayoutManager = LinearLayoutManager(context)
        binding.roverPhotoList.layoutManager = mLayoutManager
        binding.roverPhotoList.adapter = photoAdapter


        binding.roverPhotoList.addOnScrollListener(
            RoverScrollListener(
                layoutManager = mLayoutManager,
                roverName = roverName,
                viewModel = viewModel
            )
        )

        getPhotos()

    }

    private fun getPhotos() {
        viewModel.getRoverPhotos(roverName, Constants.DEFAULT_SOL)
        viewModel.getRoverCameras(roverName, Constants.DEFAULT_SOL)
    }

    private fun observeNasaState() {
        viewModel.nasaStateLiveData.observe(viewLifecycleOwner) { state ->
            nasaState = state
            checkNasaState()
        }
    }

    private fun observeCameraState() {
        viewModel.cameraStateLiveData.observe(viewLifecycleOwner) { state ->
            state.data?.let {
                cameras.clear()
                cameras.addAll(it)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkNasaState() {
        if (nasaState.loading)
            binding.progressBar.visibility = View.VISIBLE

        if (nasaState.errorMessage.isNotEmpty()) {
            binding.progressBar.visibility = View.GONE
            Log.d("ViewPagerFragment", "error: ${nasaState.errorMessage}")
        }

        nasaState.data?.let { nasa ->
            binding.progressBar.visibility = View.GONE
            if (nasa.photos.isEmpty()) viewModel.gotAllPage()
            else {
                photoList.addAll(nasa.photos)
                photoAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_filter)
            showFilterDialog()

        return false
    }

    private fun showFilterDialog() {

        val dialogView = layoutInflater.inflate(R.layout.filter_dialog, null, false)

        val radioGroup: RadioGroup = dialogView.findViewById(R.id.radioGroup)
        removeOtherButtons(radioGroup)

        materialAlertDialogBuilder.setView(dialogView)
            .setTitle("Camera Filter")
            .setMessage("Select a Camera")
            .setCancelable(true)
            .setPositiveButton("Apply") { dialog, _ ->
                val id = radioGroup.checkedRadioButtonId
                val selected: RadioButton = dialogView.findViewById(id)
                applyFilter(selected.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = materialAlertDialogBuilder.create()
        dialog.show()
    }

    private fun applyFilter(selectedCamera: String) {
        if (viewModel.camera != selectedCamera) {
            viewModel.camera = selectedCamera
            viewModel.resetPage()
            photoList.clear()
            getPhotos()
        }
    }

    private fun removeOtherButtons(radioGroup: RadioGroup) {
        radioGroup.forEach {
            val radioButton: RadioButton = it as RadioButton

            if (viewModel.camera == radioButton.text)
                radioButton.isChecked = true

            if (!cameras.contains(radioButton.text) && radioButton.text != Constants.CAMERA_ALL)
                radioButton.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}