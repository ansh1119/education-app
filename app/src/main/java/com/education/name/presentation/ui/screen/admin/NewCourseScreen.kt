package com.education.name.presentation.ui.screen.admin

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.education.name.presentation.viewmodel.CourseViewModel
import com.education.name.util.UiState

@Composable
fun NewCourseScreen(
    viewModel: CourseViewModel = hiltViewModel(),
    onCourseCreated: () -> Unit = {} // optional navigation callback
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val addCourseState by viewModel.addCourseState.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create New Course", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Course Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable { imagePickerLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = "Course Thumbnail",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    "Tap to select thumbnail",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.addCourse(
                    name.trim(),
                    description.trim(),
                    selectedImageUri
                )
            },
            enabled = addCourseState !is UiState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (addCourseState is UiState.Loading) "Creating..." else "Create Course")
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (val state = addCourseState) {
            is UiState.Error -> Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error
            )

            is UiState.Success -> {
                Text(
                    text = "Course created successfully!",
                    color = MaterialTheme.colorScheme.primary
                )
                // optional: navigate after success
                LaunchedEffect(Unit) {
                    onCourseCreated()
                }
            }

            else -> {}
        }
    }
}
