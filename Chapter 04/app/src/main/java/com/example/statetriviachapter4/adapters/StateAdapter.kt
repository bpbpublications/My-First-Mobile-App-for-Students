package com.example.statetriviachapter4.adapters
// imports
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.statetriviachapter4.databinding.CardItemBinding
import com.example.statetriviachapter4.models.State

// class StateAdapter with constructor
class StateAdapter(
    private val states: List<State>,
    private val onClick: (State) -> Unit
) : RecyclerView.Adapter<StateAdapter.ViewHolder>() {
    // methods 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    // methods 2
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val state = states[position] // get the state at the current position
        holder.bind(state)           // bind the state to the view holder
        holder.itemView.setOnClickListener {
            onClick(state) // onClick is a lambda function
        }
    }
    // methods 3
    override fun getItemCount() = states.size
    // inner class ViewHolder with constructor
    class ViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(state: State) {
            binding.tvName.text = state.name
            binding.tvTitle.text = state.title
        }
    }
}
