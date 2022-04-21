
package im.vector.app.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import im.vector.app.R
import im.vector.app.adapter.PageAdapter
import im.vector.app.features.MainActivity

class WhatsApp : AppCompatActivity() {
    private val adapter = PageAdapter(supportFragmentManager)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_app)
//        val fragment: Fragment = ChatsFragment.newInstance()
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.rec, fragment, "main_fragment")
//        transaction.commit()
        tabs()
        goToWhatsApp()
    }

    private fun goToWhatsApp() {
        val goBackBtn: Button = findViewById(R.id.btn_agree_tos)
        goBackBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun goMessageBtn() {
        val goBackBtn: Button = findViewById(R.id.fab_bottom)
        goBackBtn.setOnClickListener {
//            Toast.makeText(this.applicationContext,"clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun tabs() {
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_camera_alt_24)
        val tabDefault: TabLayout.Tab? = tabs.getTabAt(1)
        tabDefault?.select()
    }
}
