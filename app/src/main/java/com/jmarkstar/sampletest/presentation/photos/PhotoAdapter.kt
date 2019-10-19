package com.jmarkstar.sampletest.presentation.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmarkstar.sampletest.databinding.FragmentPhotosItemBinding
import com.jmarkstar.sampletest.models.Photo
import com.jmarkstar.sampletest.presentation.base.BindableAdapter

class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>(),
    BindableAdapter<Photo> {

    var items = emptyList<Photo>()
    var onItemClick: ((Photo) -> (Unit))? = null

    override fun setData(items: List<Photo>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = FragmentPhotosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding, onItemClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class PhotoViewHolder(private val binding: FragmentPhotosItemBinding, onItemClick: ((Photo) -> (Unit))?): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.listener = object: OnPhotoItemClickListener{
                override fun onPhotoItemClick(photo: Photo) {
                    onItemClick?.invoke(photo)
                }
            }
        }

        fun bind(photo: Photo){
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    interface OnPhotoItemClickListener {
        fun onPhotoItemClick(photo: Photo)
    }
}
