package com.jmarkstar.sampletest.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmarkstar.sampletest.databinding.FragmentUsersItemBinding
import com.jmarkstar.sampletest.models.User

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    var items = ArrayList<User>()

    var onClickItem: ( (User) -> Unit)? = null

    fun addItems(newItems: List<User>){
        this.items.clear()
        this.items.addAll(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = FragmentUsersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind()
    }

    inner class UserViewHolder(private val binding: FragmentUsersItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clUserItem.setOnClickListener {
                onClickItem?.invoke(items[adapterPosition])
            }
        }

        fun bind(){
            binding.user = items[adapterPosition]
            binding.executePendingBindings()
        }
    }
}
