package com.tonicapp.marsphotos

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tonicapp.marsphotos.network.MarsProperty
import com.tonicapp.marsphotos.ui.overview.MarsPhotoAdapter
import com.tonicapp.marsphotos.ui.overview.NetworkStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsProperty>?){
    val adapter = recyclerView.adapter as MarsPhotoAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun loadImgFromUrl(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.loading_animation))
            .into(imgView)
    }
}

@BindingAdapter("marsApiStatus")
fun setImgByApiStatus(imgView: ImageView, apiStatus: NetworkStatus){
    when(apiStatus){
        NetworkStatus.LOADING -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.loading_animation)
        }
        NetworkStatus.ERROR -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_connection_error)
        }
        NetworkStatus.DONE -> {
            imgView.visibility = View.GONE
        }
    }
}