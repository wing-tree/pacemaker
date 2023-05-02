package wing.tree.pacemaker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import wing.tree.pacemaker.domain.entities.Routine
import wing.tree.pacemaker.domain.entities.Time

@Entity(tableName = "routine")
data class Routine(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,
    override val title: String,
    override val description: String,
    override val periodic: Periodic,
    override val startDay: Int,
    override val endDay: Int,
    override val begin: Time,
    override val end: Time,
) : Routine()
