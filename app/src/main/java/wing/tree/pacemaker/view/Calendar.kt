package wing.tree.pacemaker.view

import android.icu.util.Calendar
import android.text.TextPaint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import wing.tree.pacemaker.data.extension.month
import wing.tree.pacemaker.data.extension.year
import wing.tree.pacemaker.domain.constant.ONE
import wing.tree.pacemaker.domain.constant.THREE_QUARTERS
import wing.tree.pacemaker.domain.entity.Status
import wing.tree.pacemaker.domain.extension.float
import wing.tree.pacemaker.domain.extension.half
import wing.tree.pacemaker.domain.usecase.core.Result
import wing.tree.pacemaker.domain.usecase.core.getOrNull
import wing.tree.pacemaker.model.Day
import wing.tree.pacemaker.model.Instance
import wing.tree.pacemaker.model.Month
import wing.tree.pacemaker.model.Week
import wing.tree.pacemaker.ui.theme.Typography
import wing.tree.pacemaker.viewmodel.MainViewModel

fun <T> emptyImmutableList() = listOf<T>().toImmutableList()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    val pageCount = Int.MAX_VALUE
    val initialPage = pageCount.half
    val pagerState = rememberPagerState(initialPage = initialPage)
    val currentPage = pagerState.currentPage
    val calendar = Calendar.getInstance().apply {
        add(Calendar.MONTH, currentPage.minus(initialPage))
    }

    val year = calendar.year
    val month = calendar.month

    val selectedDay by viewModel.selectedDay.collectAsStateWithLifecycle()
    val instances by viewModel.loadInstances(
        year = year,
        month = month,
    ).collectAsStateWithLifecycle(initialValue = null)

    Column(modifier = modifier) {
        Text(text = "${calendar[Calendar.YEAR]} ${calendar[Calendar.MONTH].inc()}")

        HorizontalPager(
            pageCount = pageCount,
            state = pagerState,
        ) { page ->
            with(Calendar.getInstance()) {
                add(Calendar.MONTH, page.minus(initialPage))

                Month(
                    month = Month(
                        month = get(Calendar.MONTH),
                        year = get(Calendar.YEAR),
                    ),
                    selectedDay = selectedDay,
                    onDaySelected = {
                        viewModel.onDaySelected(it)
                    },
                    instances = instances?.getOrNull() ?: emptyImmutableList(),
                    modifier = Modifier.wrapContentHeight(),
                )
            }
        }

        when (val result = instances) {
            is Result.Complete.Success -> Instances(
                instances = result.data,
                selectedDay = selectedDay,
                onStatusClick = {
                    viewModel.updateStatus(it)
                },
                modifier = Modifier.fillMaxWidth(),
            )

            else -> {

            }
        }
    }
}

@Composable
fun Month(
    month: Month,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    instances: ImmutableList<Instance>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        month.weeks.forEach {
            Week(
                month = month.month,
                week = it,
                selectedDay = selectedDay,
                onDaySelected = onDaySelected,
                instances = instances,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun Week(
    month: Int,
    week: Week,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    instances: ImmutableList<Instance>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        week.days.forEach { day ->
            val alpha = if (day.month == month) {
                1F
            } else {
                0.38F
            }

            Day(
                day = day,
                onDaySelected = onDaySelected,
                instances = instances.filter { it.day == day.julianDay }.toImmutableList(),
                modifier = Modifier
                    .weight(ONE.float)
                    .run {
                        if (day.julianDay == selectedDay) {
                            border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(4.dp),
                            )
                        } else {
                            this
                        }
                    }
                    .alpha(alpha),
            )
        }
    }
}

@Composable
private fun Day(
    day: Day,
    onDaySelected: (Int) -> Unit,
    instances: ImmutableList<Instance>,
    modifier: Modifier = Modifier,
) {
    val completionRate = with(instances) {
        count {
            it.status == Status.Done
        }.div(count().float)
    }

    val startAngle = 150F
    val sweepAngle = 240F

    val completeSweepAngle by animateFloatAsState(
        targetValue = sweepAngle.times(completionRate)
    )

    Canvas(
        modifier = modifier
            .aspectRatio(THREE_QUARTERS)
            .clickable {
                onDaySelected(day.julianDay)
            },
    ) {
        val nativeCanvas = drawContext.canvas.nativeCanvas
        val text = "${day.dayOfMonth}"
        val textPaint = TextPaint().apply {
            color = android.graphics.Color.WHITE
            textSize = 12.sp.toPx()
        }

        val x = size.width.half.minus(textPaint.measureText(text).half)
        val y = 12.sp.toPx()

        nativeCanvas.drawText(
            text,
            x,
            y,
            textPaint,
        )

        if (instances.isNotEmpty()) {
            drawArc(
                size = Size(72f, 72f),
                color = Color.Gray,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = 6f,
                    cap = StrokeCap.Round,
                ),
                topLeft = Offset(
                    x = (size.width - 72f).half,
                    y = (size.height - 72f).half,
                )
            )

            drawArc(
                size = Size(72f, 72f),
                color = Color.Red,
                startAngle = startAngle,
                sweepAngle = completeSweepAngle,
                useCenter = false,
                style = Stroke(
                    width = 6f,
                    cap = StrokeCap.Round,
                ),
                topLeft = Offset(
                    x = (size.width - 72f).half,
                    y = (size.height - 72f).half,
                )
            )
        }
    }
}

@Composable
private fun Instances(
    instances: ImmutableList<Instance>,
    selectedDay: Int,
    onStatusClick: (Instance) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            instances.filter {
                it.day == selectedDay
            },
        ) { instance ->
            Instance(
                instance = instance,
                onStatusClick = onStatusClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun Instance(
    instance: Instance,
    onStatusClick: (Instance) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier,
    ) {
        with(instance) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(ONE.float)) {
                    Text(
                        text = title,
                        style = Typography.bodyLarge,
                    )
                    Text(
                        text = description,
                        style = Typography.bodyMedium,
                    )
                }

                Button(
                    onClick = {
                        onStatusClick(instance)
                    },
                ) {
                    Text(text = status.name)
                }
            }
        }
    }
}
