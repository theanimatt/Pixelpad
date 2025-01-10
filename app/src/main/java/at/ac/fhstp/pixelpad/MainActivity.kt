package at.ac.fhstp.pixelpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import at.ac.fhstp.pixelpad.ui.navigation.NoteNavigation
import at.ac.fhstp.pixelpad.ui.theme.PixelPadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PixelPadTheme {
                Surface {
                    NoteNavigation()
                }
            }
        }
    }
}
