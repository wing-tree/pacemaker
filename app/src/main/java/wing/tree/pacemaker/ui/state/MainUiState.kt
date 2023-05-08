package wing.tree.pacemaker.ui.state

class MainUiState {

    sealed interface Behaviour {
        object Expanded : Behaviour
        object PartiallyExpanded : Behaviour
        object Collapsed : Behaviour
    }
}