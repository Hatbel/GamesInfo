package com.hatbel.gamesinfo.domain.di

import com.hatbel.gamesinfo.data.models.Game
import com.hatbel.gamesinfo.data.models.GameDetails
import com.hatbel.gamesinfo.data.models.GameDetailsUI
import com.hatbel.gamesinfo.data.models.GameUI
import com.hatbel.gamesinfo.data.repositories.GamesRepository
import com.hatbel.gamesinfo.domain.usecase.GetGameByIdUseCase
import com.hatbel.gamesinfo.domain.usecase.GetGamesListUseCase
import com.hatbel.gamesinfo.presenter.features.gamesList.FragmentListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val mappersModule = module {
    factory<Game.Mapper<GameUI>>(named("gameUIMapper")) {
        Game.Mapper.Base()
    } bind Game.Mapper::class

    factory<GameDetails.Mapper<GameDetailsUI>>(named("gameDetailsUIMapper")) {
        GameDetails.Mapper.Base()
    } bind GameDetails.Mapper::class
}

val useCaseModule = module {
    factory<GetGamesListUseCase>(named("gamesListUseCase")) {
        GetGamesListUseCase.Base(
            get(named("gamesRepository")),
            get(named("gameUIMapper"))
        )
    } bind GetGamesListUseCase::class

    factory<GetGameByIdUseCase>(named("gameByIdUseCase")) {
        GetGameByIdUseCase.Base(
            get(named("gamesRepository")),
            get(named("gameDetailsUIMapper"))
        )
    } bind GetGameByIdUseCase::class
}

val appModule = module {

    //ViewModels
    viewModel {
        FragmentListViewModel(
            get(named("gamesListUseCase")),
            get(named("gameByIdUseCase"))
        )
    }

    //Repositories
    single<GamesRepository>(named("gamesRepository")) {
        GamesRepository.Base(
            provideApiService(get())
        )
    } bind GamesRepository::class
}