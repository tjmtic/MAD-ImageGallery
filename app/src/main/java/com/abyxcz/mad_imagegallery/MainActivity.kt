package com.abyxcz.mad_imagegallery

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.abyxcz.mad_imagegallery.databinding.Camera2ImageCaptureBinding
import com.abyxcz.mad_imagegallery.databinding.MyFragmentLayoutBinding
import com.abyxcz.mad_imagegallery.ui.theme.MADImageGalleryTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MADImageGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    FragmentContainerView()
                }
            }
        }
    }
}

@Composable
fun FragmentContainerView() {
    /*AndroidView(factory = { context ->
        // This is the FrameLayout in which the Fragment will be placed
        FrameLayout(context).apply {
            // Generate a unique ID for this view to identify it during Fragment transactions
            id = View.generateViewId()
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            // Perform the Fragment transaction
            supportFragmentManager.commit {
                replace(id, Camera2ImageCapture())
            }
        }
    })*/

    AndroidViewBinding(MyFragmentLayoutBinding::inflate) {
        val myFragment = fragmentContainerView.getFragment<Camera2ImageCapture>()
        // ...
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MADImageGalleryTheme {
        Greeting("Android")
    }
}