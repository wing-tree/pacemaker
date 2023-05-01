package wing.tree.pacemaker.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import wing.tree.pacemaker.ui.theme.PacemakerTheme
import wing.tree.pacemaker.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.scheduleCreateInstancesWorker()
        setContent {
            PacemakerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    startActivity(Intent(this, CreateRoutineActivity::class.java))
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = null,
                                )
                            }
                        }
                    ) { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            Calendar()
                        }
                    }
                }
            }
        }
    }
}
