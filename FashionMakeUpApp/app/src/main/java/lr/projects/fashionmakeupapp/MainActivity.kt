package lr.projects.fashionmakeupapp

import android.content.res.Configuration
import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import lr.projects.fashionmakeupapp.ui.MakeUpApp
import lr.projects.fashionmakeupapp.ui.makeuplist.MakeUpDetailsViewModel
import lr.projects.fashionmakeupapp.ui.makeuplist.MakeUpListViewModel
import lr.projects.fashionmakeupapp.ui.theme.FashionMakeUpAppTheme

@AndroidEntryPoint
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FashionMakeUpAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MakeUpApplication()
                }
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun MakeUpApplication(){
    MaterialTheme (
       colorScheme = if(isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ){
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            topBar ={ TopAppBar(
              title = {  Text(text = stringResource(id = R.string.app_name), color = Color.White )},
              colors =  TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.darkPink))
          ) },
            snackbarHost = {  SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                MakeUpApp(snackbarHostState)
            }
        }
    }
}