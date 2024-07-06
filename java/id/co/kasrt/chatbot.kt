package id.co.kasrt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class chatbot : AppCompatActivity() {

    private val messageList = mutableListOf<Message>()
    private lateinit var messageAdapter: MessageAdapter
    private val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        val buttonSend = findViewById<Button>(R.id.buttonSend)
        val editTextMessage = findViewById<EditText>(R.id.editTextMessage)

        // Initialize the RecyclerView with a LinearLayoutManager and the MessageAdapter
        messageAdapter = MessageAdapter(messageList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@chatbot)
            adapter = messageAdapter
        }

        // Set an OnClickListener on the send button
        buttonSend.setOnClickListener {
            val messageText = editTextMessage.text.toString()
            if (messageText.isNotBlank()) {
                // Add user's message to the list
                addMessage(messageText, true)
                editTextMessage.text.clear()

                // Determine bot's response based on the user's message
                val botResponse = when {
                    messageText.contains("ilmu yang mempelajari makhluk hidup disebut", ignoreCase = true) -> "Bot: Biologi"
                    else -> "Bot: Maaf, saya tidak mengerti pertanyaan Anda."
                }

                // Add bot's response to the list
                addMessage(botResponse, false)
            }
        }
    }

    private fun addMessage(text: String, isSentByUser: Boolean) {
        messageList.add(Message(text, isSentByUser))
        messageAdapter.notifyItemInserted(messageList.size - 1)

        recyclerView.scrollToPosition(messageList.size - 1)
    }
}
