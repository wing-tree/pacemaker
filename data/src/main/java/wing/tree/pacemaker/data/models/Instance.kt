package wing.tree.pacemaker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entities.Instance
import wing.tree.pacemaker.domain.entities.Routine
import wing.tree.pacemaker.domain.entities.Status
import wing.tree.pacemaker.domain.extension.long

@Entity(tableName = "instance")
data class Instance(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = ZERO.long,
    override val routineId: Long,
    override val title: String,
    override val description: String,
    override val periodic: Routine.Periodic,
    override val begin: Long,
    override val end: Long,
    override val day: Int,
    override val status: Status,
) : Instance
