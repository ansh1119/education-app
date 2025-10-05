package com.education.name.di

import com.education.name.data.datasource.AuthDataSource
import com.education.name.data.datasource.UserDataSource
import com.education.name.data.repository.AuthRepositoryImpl
import com.education.name.data.repository.UserRepository
import com.education.name.domain.repository.AuthRepository
import com.education.name.domain.usecase.SignInUseCase
import com.education.name.domain.usecase.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(firebaseAuth: FirebaseAuth): AuthDataSource =
        AuthDataSource(firebaseAuth)

    @Provides
    @Singleton
    fun provideUserDataSource(firestore: FirebaseFirestore, auth:FirebaseAuth): UserDataSource =
        UserDataSource(firestore,auth)

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository =
        AuthRepositoryImpl(firebaseAuth, firestore)

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase =
        SignUpUseCase(repository)

    @Provides
    @Singleton
    fun provideSignInUseCase(repository: AuthRepository, userRepository: UserRepository): SignInUseCase =
        SignInUseCase(repository, userRepository = userRepository)

    @Provides
    @Singleton
    fun provideUserRpository(dataSource: UserDataSource, firestore: FirebaseFirestore): UserRepository=
        UserRepository(dataSource,firestore)
}
