package im.vector.app.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import im.vector.app.R

class RecyclerViewAdapter(val listData: List<DataModel>, val clickListener: ClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
        var messageTextView: TextView = view.findViewById(R.id.titleTextView2)
        var dateTextView: TextView = view.findViewById(R.id.titleTextView3)
        var ivImage: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.titleTextView.text = listData[position].title
        holder.messageTextView.text = listData[position].messageT
        holder.dateTextView.text = listData[position].dateT
        listData[position].image?.let { holder.ivImage.setImageResource(it) }
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(listData[position])
        }
    }

    interface ClickListener {
        fun onItemClick(dataModel: DataModel)
    }
}
