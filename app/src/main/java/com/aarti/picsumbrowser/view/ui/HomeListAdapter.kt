package com.aarti.picsumbrowser.view.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aarti.picsumbrowser.R
import com.aarti.picsumbrowser.model.PhotoListResponse
import com.aarti.picsumbrowser.view.utils.Constants
import com.bumptech.glide.Glide

class HomeListAdapter(
    private var context: Context?,
    private var dataList: List<PhotoListResponse>
) : RecyclerView.Adapter<HomeListAdapter.HomeHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.photo_list_item, parent, false)
        return HomeHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.tv_author.text = dataList.get(position).author

        Glide.with(context!!)
            .load(Constants.IMAGE_URL+dataList.get(position).id)
            .into(holder.iv_photo)
    }


    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_author = itemView.findViewById(R.id.tv_author) as TextView
        val iv_photo = itemView.findViewById(R.id.iv_photo) as ImageView
    }

}