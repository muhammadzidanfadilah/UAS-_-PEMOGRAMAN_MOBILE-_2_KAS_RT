package id.co.kasrt

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class digitalization : AppCompatActivity() {

    private lateinit var documentImageView: ImageView
    private lateinit var captureButton: Button
    private lateinit var uploadButton: Button
    private lateinit var storageRef: StorageReference
    private lateinit var db: FirebaseFirestore

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digitization)

        // Inisialisasi Firebase Storage dan Firestore
        storageRef = FirebaseStorage.getInstance().reference
        db = FirebaseFirestore.getInstance()

        // Menghubungkan view dengan variabel
        documentImageView = findViewById(R.id.documentImageView)
        captureButton = findViewById(R.id.captureButton)
        uploadButton = findViewById(R.id.uploadButton)

        // Set listener untuk tombol ambil gambar
        captureButton.setOnClickListener {
            dispatchTakePictureIntent()
        }

        // Set listener untuk tombol upload
        uploadButton.setOnClickListener {
            uploadImageToFirebase()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            documentImageView.setImageBitmap(imageBitmap)
        }
    }

    private fun uploadImageToFirebase() {
        val imageBitmap = (documentImageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imagesRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")
        val uploadTask = imagesRef.putBytes(data)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            val downloadUrl = taskSnapshot.storage.downloadUrl
            Toast.makeText(this, "Upload berhasil: $downloadUrl", Toast.LENGTH_SHORT).show()
            // Simpan URL gambar ke Firestore atau lakukan operasi lainnya
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Upload gagal: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}