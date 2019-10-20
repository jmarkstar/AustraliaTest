package com.jmarkstar.sampletest.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmarkstar.sampletest.databinding.FragmentUsersItemBinding
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.presentation.common.BindableAdapter

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>(), BindableAdapter<User> {

    var items = emptyList<User>()
    var onItemClick: ((User) -> (Unit))? = null

    override fun setData(items: List<User>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = FragmentUsersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onItemClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class UserViewHolder(private val binding: FragmentUsersItemBinding, onItemClick: ((User) -> (Unit))?): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.listener = object: OnUserItemClickListener{
                override fun onUserItemClick(user: User) {
                    onItemClick?.invoke(user)
                }
            }
        }

        fun bind(user: User){
            binding.user = user
            binding.executePendingBindings()
        }
    }

    interface OnUserItemClickListener {
        fun onUserItemClick(user: User)
    }
}
