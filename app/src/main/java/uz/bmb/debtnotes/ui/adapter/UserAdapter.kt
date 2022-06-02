package uz.bmb.debtnotes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.bmb.debtnotes.databinding.ItemSelectedBinding
import uz.bmb.debtnotes.model.Model

class UserAdapter(private val itemClickListener: UserItemListener) :
    RecyclerView.Adapter<UserAdapter.VH>() {
private var list= arrayListOf<Model>()
    interface UserItemListener {
        fun onClick(user: Model)
    }
    fun updateData(list:ArrayList<Model>){
        this.list=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSelectedBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(list[position])
        }
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

    class VH(private val binding: ItemSelectedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: Model) {
            binding.user = user
        }
    }
}