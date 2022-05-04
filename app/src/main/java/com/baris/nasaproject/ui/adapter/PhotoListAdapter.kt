package com.baris.nasaproject.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baris.nasaproject.R
import com.baris.nasaproject.common.downloadFromUrl
import com.baris.nasaproject.data.remote.nasa.dto.Photo
import com.baris.nasaproject.databinding.ListItemBinding


class PhotoListAdapter(
    private val photoList: ArrayList<Photo>,
    private val view: View
): RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {

    private val isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context: Context = holder.binding.root.context
        holder.binding.roverPhoto.downloadFromUrl(context, photoList[position].imgSrc)
        holder.binding.roverPhoto.setOnClickListener{
            showPopUp(photoList[position])
        }
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun showPopUp(photo: Photo){
        val inflater = view.context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.rover_image_pop_up, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupView.setOnTouchListener(object : View.OnTouchListener {
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                popupWindow.dismiss()
                return true
            }
        })
        // Objects
        val roverPhoto: ImageView = popupView.findViewById(R.id.roverPhoto)
        val earthDate: TextView = popupView.findViewById(R.id.earthDate)
        val roverName: TextView = popupView.findViewById(R.id.roverName)
        val cameraName: TextView = popupView.findViewById(R.id.cameraName)
        val roverStatus: TextView = popupView.findViewById(R.id.roverStatus)
        val launchDate: TextView = popupView.findViewById(R.id.roverLaunchDate)
        val landDate: TextView = popupView.findViewById(R.id.roverLandDate)

        roverPhoto.downloadFromUrl(popupView.context, photo.imgSrc)
        earthDate.text = "Date: ${photo.earthDate}"
        roverName.text = "Rover: ${photo.rover.name}"
        cameraName.text = "Camera: ${photo.camera.name}"
        roverStatus.text = "Rover Status: ${photo.rover.status}"
        launchDate.text = "Launching Date: ${photo.rover.launchDate}"
        landDate.text = "Landing Date: ${photo.rover.landingDate}"
    }

    override fun getItemCount(): Int = photoList.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    class ViewHolder(var binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)
}