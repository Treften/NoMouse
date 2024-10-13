package com.example.nomouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Item(
    var mouseTouchNum:Int,
    var touchNum:Int,
    var gameTime:Float,
    var hitRate: Float
)

class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val list: MutableList<Item> = mutableListOf()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        val helper = DbHelper(this)
        val db = helper.writableDatabase
        val c = db.query("GameStats",
        arrayOf("id", "touchNum", "mouseTouchNum", "gameTime"),
        null,
        null,
        null, null, null, null)
        val totalRows = c.count
        if (totalRows > 0) {
            c.moveToLast()
            val limit = Math.min(10, totalRows)

            for (i in 0 until limit) {
                val id = c.getInt(c.getColumnIndexOrThrow("id"))
                val touchNum = c.getInt(c.getColumnIndexOrThrow("touchNum"))
                val mouseTouchNum = c.getInt(c.getColumnIndexOrThrow("mouseTouchNum"))
                val gameTime = c.getFloat(c.getColumnIndexOrThrow("gameTime"))
                var hitRate = 0f
                if (touchNum != 0) {
                    hitRate = mouseTouchNum.toFloat() / touchNum
                }
                list.add(Item(mouseTouchNum, touchNum, gameTime/1000, hitRate))
                if (!c.moveToPrevious()) break
            }
        }
        c.close()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CustomRecyclerAdapter(list)
        recyclerView.adapter = adapter
    }
}
class CustomRecyclerAdapter(private val items: List<Item>): RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    fun setOnItemClickListener(f: (Int) -> Unit) { onItemClickListener = f }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameTimeTextView: TextView = itemView.findViewById(R.id.textViewGameTime)
        val touchNumTextView: TextView = itemView.findViewById(R.id.textViewTouchNum)
        val mouseTouchNumTextView: TextView = itemView.findViewById(R.id.textViewMouseTouchNum)
        val hitRateTextView: TextView = itemView.findViewById(R.id.textViewHitRate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, null, false)
        val holder = MyViewHolder(itemView)

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.gameTimeTextView.text = "Время игры: "+items[position].gameTime.toString()+" секунд"
        holder.touchNumTextView.text = "Количество нажатий: "+items[position].touchNum.toString()
        holder.mouseTouchNumTextView.text = "Количество попаданий: "+items[position].mouseTouchNum.toString()
        holder.hitRateTextView.text = "Процент попаданий: "+items[position].hitRate.toString()+"%"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}