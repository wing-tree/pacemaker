package wing.tree.pacemaker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entity.Instance
import wing.tree.pacemaker.domain.entity.Status
import wing.tree.pacemaker.domain.extension.long

@Entity(tableName = "instance")
data class Instance(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = ZERO.long,
    override val routineId: Long,
    override val title: String,
    override val description: String,
    override val begin: Time,
    override val end: Time,
    override val day: Int,
    override val status: Status,
) : Instance
