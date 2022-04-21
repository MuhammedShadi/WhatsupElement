package im.vector.app.features

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import im.vector.app.R
import im.vector.app.fragments.WhatsApp

class DetailsActivity3 : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details3)

        val b: Bundle? = intent.extras
        b!!.getInt("puzzle")
        val textView  = findViewById<TextView>(R.id.titleTextView1)
        textView.text = b.getString("puzzle")


        val b2: Bundle? = intent.extras
        b2!!.getInt("puzzle2")
        val textView2  = findViewById<TextView>(R.id.messageSent)
        textView2.text = b.getString("puzzle2")


        val b3: Bundle? = intent.extras
        b3!!.getInt("puzzle3")
        val textView3  = findViewById<TextView>(R.id.date3)
        textView3.text = b.getString("puzzle3")



        val extras = intent.extras
        val byteArray = extras!!.getByteArray("picture")
        val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        val image: ImageView = findViewById<View>(R.id.imageView1) as ImageView

        image.setImageBitmap(bmp)

        backToChats()

    }

    private fun backToChats() {
        val goBackBtn: Button = findViewById(R.id.button_back1)
        goBackBtn.setOnClickListener {
            val intent = Intent(this, WhatsApp::class.java)
            startActivity(intent)
            finish()
        }
    }
}
