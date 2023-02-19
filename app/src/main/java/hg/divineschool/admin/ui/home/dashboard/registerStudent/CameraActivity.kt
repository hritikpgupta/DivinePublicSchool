package hg.divineschool.admin.ui.home.dashboard.registerStudent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Camera
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import hg.divineschool.admin.R
import hg.divineschool.admin.ui.utils.getActivity
import hg.divineschool.admin.ui.utils.getCameraProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraActivity : ComponentActivity() {
    private lateinit var outputDirectory: File
    lateinit var clickedPhotoUri: Uri
    private lateinit var cameraExecutor: ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        setContent {
            requestFullScreen(LocalView.current)
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                CameraView(outputDirectory = outputDirectory,
                    executor = cameraExecutor,
                    onImageCaptured = { uri ->
                        clickedPhotoUri = uri
                        setResult(RESULT_OK, Intent().putExtra("imageUri", uri.toString()))
                        finish()
                    },
                    onError = {})
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()

    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun requestFullScreen(view: View) {
        val window = view.context.getActivity()!!.window
        WindowCompat.getInsetsController(window, view).hide(
            WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars()
        )
    }
    private fun takePhoto(
        fileNameFormat: String,
        imageCapture: ImageCapture,
        outputDirectory: File,
        executor: Executor,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(fileNameFormat, Locale.UK).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
                Log.e("DPS Camera", "Take photo error", exception)
            }

        })
    }

    @Composable
    fun CameraView(
        outputDirectory: File,
        executor: Executor,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        val lensFacing = CameraSelector.LENS_FACING_BACK
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val preview = Preview.Builder().build()
        val previewView = remember { PreviewView(context) }
        val imageCapture: ImageCapture = remember {
            ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetResolution(android.util.Size(480, 640)).build()
        }
        val cameraSelector = CameraSelector.Builder().requireLensFacing(LENS_FACING_BACK)
            .requireLensFacing(lensFacing).build()

        LaunchedEffect(lensFacing) {
            val cameraProvider = context.getCameraProvider()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
            preview.setSurfaceProvider(previewView.surfaceProvider)
        }

        Box(

        ) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
            Surface(shape = CutOutShape(), color = Color.Black.copy(0.45f), modifier = Modifier.fillMaxSize()) {}
            Column(
                modifier = Modifier.padding(top = 10.dp, start = 32.dp, end = 32.dp, bottom = 54.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { finish() }) {
                        Icon(
                            imageVector = Icons.TwoTone.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        "Back", color = Color.White, fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "Align face in the frame.",
                    color = Color.White,
                    fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                IconButton(modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally),
                    onClick = {
                        takePhoto(
                            fileNameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                            imageCapture = imageCapture,
                            outputDirectory = outputDirectory,
                            executor = executor,
                            onImageCaptured = onImageCaptured,
                            onError = onError
                        )
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Sharp.Camera,
                            contentDescription = "Take picture",
                            tint = Color.White,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(10.dp)
                                .border(1.dp, Color.White, CircleShape)
                        )

                    })
            }
        }
    }

}









