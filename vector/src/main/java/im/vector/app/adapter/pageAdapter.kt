@file:Suppress("DEPRECATION")

package im.vector.app.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import im.vector.app.fragments.CallsFragment
import im.vector.app.fragments.CameraFragment
import im.vector.app.fragments.ChatsFragment
import im.vector.app.fragments.StatusFragment

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return  4
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                CameraFragment()
            }
            1 -> {
                ChatsFragment()
            }
            2 -> {
                StatusFragment()
            }
            3 -> {
                CallsFragment()
            }
            else -> ChatsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            1->{return  "Chats"}
            2->{return  "Status"}
            3->{return  "Calls"}
        }
        return super.getPageTitle(position)
    }
}
