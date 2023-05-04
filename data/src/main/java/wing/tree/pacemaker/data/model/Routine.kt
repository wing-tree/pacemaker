package wing.tree.pacemaker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import wing.tree.pacemaker.domain.entity.Routine

@Entity(tableName = "routine")
data class Routine(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,
    override val title: String,
    override val description: String,
    override val startDay: Int,
    override val endDay: Int,
    override val begin: Time,
    override val end: Time,
    override val reminder: Reminder,
) : Routine()
