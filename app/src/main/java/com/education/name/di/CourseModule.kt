package com.education.name.di

import com.education.name.data.datasource.CourseDataSource
import com.education.name.data.repository.CourseRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CourseModule {


    @Provides
    @Singleton
    fun provideCourseDataSource(firestore: FirebaseFirestore): CourseDataSource =
        CourseDataSource(firestore)

    @Provides
    @Singleton
    fun provideCourseRepository(dataSource: CourseDataSource): CourseRepository =
        CourseRepository(dataSource)
}
