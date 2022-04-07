package pt.baptista.android.training.hangmangame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HangmanHistoryAdapter internal constructor(private val context: Context):
    RecyclerView.Adapter<HangmanHistoryAdapter.ItemViewHolder>(){

    private  val inflater = LayoutInflater.from(context)

    private var items = emptyList<HangmanHistoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.wordView.text = "[${holder.number}] ${item.word}"
        holder.dateView.text = item.date.toString()
        holder.wordView.setTextColor(
            context.getColor(
                if(item.wrong.length == 7){
                    android.R.color.holo_red_light
                }else {
                    android.R.color.black
                }
            )
        )
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<HangmanHistoryItem>){
        this.items = items
        notifyDataSetChanged()
    }

    private var counter = 0;
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val number = ++counter;
        val wordView = itemView.findViewById<TextView>(android.R.id.text1)
        val dateView = itemView.findViewById<TextView>(android.R.id.text2)
    }

}