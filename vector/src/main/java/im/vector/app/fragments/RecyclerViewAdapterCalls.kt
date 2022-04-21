package im.vector.app.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import im.vector.app.R

class RecyclerViewAdapterCalls(val listData:List<DataModel>, val clickListener:ClickListener) : RecyclerView.Adapter<RecyclerViewAdapterCalls.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterCalls.MyViewHolder {
       val view =  LayoutInflater.from(parent.context).inflate(R.layout.recycler_row2,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  listData.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var titleTextView : TextView
        var messageTextView : TextView
        var callIconView : ImageButton
        var ivImage: ImageView

        init {
            titleTextView = view.findViewById(R.id.titleTextView)
            ivImage = view.findViewById(R.id.imageView)
            messageTextView = view.findViewById(R.id.titleTextView2)
            callIconView = view.findViewById(R.id.toolbar_button)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.titleTextView.text = listData.get(position).title
        holder.messageTextView.text = listData.get(position).messageT
        holder.callIconView.setImageResource(R.drawable.ic_baseline_call_24)
        listData.get(position).image?.let { holder.ivImage.setImageResource(it) }
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(listData.get(position))
        }
    }

    interface ClickListener{
        fun onItemClick(dataModel: DataModel)
    }
}
