package wing.tree.pacemaker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import wing.tree.pacemaker.domain.entities.Routine

@Entity(tableName = "routine")
data class Routine(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,
    override val title: String,
    override val description: String,
    override val periodic: Periodic,
    override val startDay: Int,
    override val endDay: Int,
    override val begin: Long,
    override val end: Long,
) : Routine()