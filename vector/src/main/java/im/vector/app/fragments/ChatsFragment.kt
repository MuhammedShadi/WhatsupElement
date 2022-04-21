package im.vector.app.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import im.vector.app.R
import im.vector.app.features.DetailsActivity3
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class ChatsFragment : Fragment(), RecyclerViewAdapter.ClickListener {
    private lateinit var adapter: RecyclerViewAdapter
    val listData: ArrayList<DataModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//      inflater.inflate(R.menu.home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chats, container, false)
        buildDisplayData()
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerViewAdapter(listData, this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL))
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.left = 20    // Left Margin.
                outRect.right = 20   // Right Margin.
                outRect.top = 16     // Top Margin.
                outRect.bottom = 16  // Bottom Margin.
            }
        })
        //right swipe on recycler row:
        val itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDialog(viewHolder)
            }
        }
        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(recyclerView)
    }

    private fun showDialog(viewHolder: RecyclerView.ViewHolder) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Deleted Item")
        builder.setMessage("Are You Sure ?")
        builder.setPositiveButton("Confirm") { _, _ ->
            val position = viewHolder.adapterPosition
            listData.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            val position = viewHolder.adapterPosition
            adapter.notifyItemChanged(position)
        }
        builder.show()
    }

    private fun buildDisplayData() {
        listData.add(DataModel("أحمد محمد", "أنت فين ياعم ؟","10/06/21", R.drawable.test))
        listData.add(DataModel("مصطفى سمير", "كنت مستنيك امبارح","08/06/21", R.drawable.tesrt))
        listData.add(DataModel("ربيع السيد شركة", "بعت الطلبيات للعملاء؟", "07/06/21",R.drawable.testqw))
        listData.add(DataModel("عماد حامد", "ابقى روح سلم على أعمامك ؟","07/06/21", R.drawable.testt))
        listData.add(DataModel("زوجتي الحبيبة", "أنت فين يا حبيبي؟","06/06/21", R.drawable.testtty))
        listData.add(DataModel("أمي الحبيبة", "وحشتيني يا أمي ", "06/06/21",R.drawable.testrt))
        listData.add(DataModel("سمية عمار", "ازيك يا بنت خالتي ؟","06/06/21", R.drawable.testyuq))
        listData.add(DataModel("سهيلة ابراهيم", "عاملة ايه يا عمتو ؟", "05/06/21",R.drawable.images))
        listData.add(DataModel("روفيدة ربيع", "روفيدة ابقى سلمي على خالتي ؟", "03/06/21",R.drawable.ic_baseline_account_circle_24))
        listData.add(DataModel("عبدالعزيز احمد", "حبيبي ازيك ؟ عامل ايه ؟","03/06/21", R.drawable.testtt))
        listData.add(DataModel("محمود خميس", "عم الناس اللي واحشني","02/06/21", R.drawable.testuq))
        listData.add(DataModel("حمدي مصطفى", "ازيك يا استاذ حمدي ؟","01/06/21", R.drawable.ic_baseline_account_circle_24))
    }

    companion object {
        fun newInstance() =
                ChatsFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun onItemClick(dataModel: DataModel) {
        val stream = ByteArrayOutputStream()
        dataModel.image?.let { BitmapFactory.decodeResource(resources, it) }?.compress(Bitmap.CompressFormat.JPEG, 1, stream)
        val byteArray: ByteArray = stream.toByteArray()
        val intent = Intent(context, DetailsActivity3::class.java)
        intent.putExtra("puzzle", dataModel.title)
        intent.putExtra("puzzle2", dataModel.messageT)
        intent.putExtra("puzzle3", "ً11:05")
        intent.putExtra("picture", byteArray);
        context?.startActivity(intent)
    }
}
