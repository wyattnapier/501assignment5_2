package com.example.assign5_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.assign5_2.ui.theme.Assign5_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assign5_2Theme {
                MainScreen()
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Notes : Screen("notes", "Notes", Icons.Default.Edit)
    data object Tasks : Screen("tasks", "Tasks", Icons.Default.Done)
    data object Calendar : Screen("calendar", "Calendar", Icons.Default.DateRange)
}

val screens = listOf(
    Screen.Notes,
    Screen.Tasks,
    Screen.Calendar
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // use screen-specific view models
            composable(Screen.Notes.route) {
                val viewModel: NotesViewModel = viewModel()
                NotesScreen(viewModel)
            }
            composable(Screen.Tasks.route) {
                val viewModel: TasksViewModel = viewModel()
                TasksScreen(viewModel)
            }
            composable(Screen.Calendar.route) {
                GenericScreen(screenName = Screen.Calendar.title)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    // This is the core navigation logic.
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items.
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true // Save the state of the screen you're leaving.
                        }
                        // Avoid multiple copies of the same destination when re-selecting the same item.
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item.
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun NotesScreen(viewModel: NotesViewModel) {
    Column(Modifier.padding(16.dp)) {
        Text("Notes", style = MaterialTheme.typography.titleLarge)

        TextField(
            value = viewModel.currentInput,
            onValueChange = { viewModel.currentInput = it },
            label = { Text("New note") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.addNote() },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add")
        }

        LazyColumn {
            items(viewModel.notes.size) { index ->
                val note = viewModel.notes[index]
                Text("• ${note.text}", Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
fun TasksScreen(viewModel: TasksViewModel) {
    Column(Modifier.padding(16.dp)) {
        Text("Tasks", style = MaterialTheme.typography.titleLarge)
        LazyColumn {
            items(viewModel.tasks.size) { index ->
                val task = viewModel.tasks[index]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = task.isDone,
                        onCheckedChange = { viewModel.toggleTask(task.id) }
                    )
                    Text(task.label)
                }
            }
        }
    }
}
@Composable
fun GenericScreen(screenName: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = screenName, style = MaterialTheme.typography.headlineMedium)
    }
}