package im.vector.app.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import im.vector.app.R

@Suppress("DEPRECATION")
class CallsFragment : Fragment(), RecyclerViewAdapterCalls.ClickListener {
    private lateinit var adapter: RecyclerViewAdapterCalls
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
        val view = inflater.inflate(R.layout.fragment_calls, container, false)
        buildDisplayData()
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerViewAdapterCalls(listData, this)
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
        listData.add(DataModel("أحمد محمد", "أمس,الساعة 9:25 مساءاً","10/06/21", R.drawable.test))
        listData.add(DataModel("مصطفى سمير", "8 يونيو,الساعة 6:45 مساءا","08/06/21", R.drawable.tesrt))
        listData.add(DataModel("ربيع السيد شركة", "7 يونيو,الساعة 4:15 مساءا", "07/06/21",R.drawable.testqw))
        listData.add(DataModel("عماد حامد", "7 يونيو,الساعة 11:32 مساءا","07/06/21", R.drawable.testt))
        listData.add(DataModel("زوجتي الحبيبة", "6 يونيو,الساعة 3:39 مساءا","06/06/21", R.drawable.testtty))
        listData.add(DataModel("أمي الحبيبة", "6 يونيو,الساعة 11:35 مساءا", "06/06/21",R.drawable.testrt))
        listData.add(DataModel("سمية عمار", "6 يونيو,الساعة 12:07 مساءا","06/06/21", R.drawable.testyuq))
        listData.add(DataModel("سهيلة ابراهيم", "5 يونيو,الساعة 3:25 مساءا", "05/06/21",R.drawable.images))
        listData.add(DataModel("روفيدة ربيع", "3 يونيو,الساعة 5:45 مساءا", "03/06/21",R.drawable.ic_baseline_account_circle_24))
        listData.add(DataModel("عبدالعزيز احمد", "3 يونيو,الساعة 1:20 مساءا","03/06/21", R.drawable.testtt))
        listData.add(DataModel("محمود خميس", "2 يونيو,الساعة 4:10 مساءا","02/06/21", R.drawable.testuq))
        listData.add(DataModel("حمدي مصطفى", "1 يونيو,الساعة 7:00 مساءا","01/06/21", R.drawable.ic_baseline_account_circle_24))
    }

    companion object {
        fun newInstance() =
                ChatsFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun onItemClick(dataModel: DataModel) {
        Toast.makeText(context,"clicked", Toast.LENGTH_SHORT).show()
    }
}
