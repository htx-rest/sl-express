<template>
	<view class="getTimePicker">
		<uni-popup ref="popup" type="bottom" :safe-area="false">
			<view class="header">
				<view class="header-title">期望上门时间</view>
				<view class="close" @click="handleCancel"></view>
			</view>
			<!-- 时间选择区域 -->
			<view class="time-select">
				<view class="select-day">
					<view :class="selectedDay === index ?'active':''" class="select-day-item"
						@click="handleSelectDay(index,item)" v-for="(item,index) in selectDay" :key='index'>
						{{item}}
					</view>
				</view>
				<view class="select-time">
					<scroll-view scroll-y :scroll-top="scrollTop" @scroll="scroll">
						<view :class="selectedTime === item.value ?'active':''" class="time-item"
							v-for="(item,index) in (selectedDay===0?todayList.todos:dateList)" :key="index"
							@click='handleSelectTime(item.value,item.label)'>
							<view class="time-value">{{item.label}}</view>
							<view class="time-select" v-if='selectedTime === item.value'></view>
						</view>
					</scroll-view>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		onMounted,
		nextTick,
		defineEmits
	} from 'vue';
	const emits = defineEmits(["@getTime"]);
	const popup = ref()
	let scrollTop = ref(0) //顶部位置
	let oldScrollTop = ref(0) //记录滑动的位置
	let selectedDay = ref(0)
	let selectedDayLabel = ref('今天')
	let selectedTime = ref(0)
	let selectedTimeLabel = ref()
	const selectDay = reactive(['今天', '明天', '后天'])
	let todayList = reactive({
		todos: [{
			label: '一小时内',
			value: 1
		}]
	})
	onMounted(() => {
		todayList.todos = [...todayList.todos.concat(
			dateList.filter(item => item.value > new Date().getHours())
		)]
	})
	const dateList = reactive(
		[{
				label: '08:00-09:00',
				value: 9
			},
			{
				label: '09:00-10:00',
				value: 10
			},
			{
				label: '10:00-11:00',
				value: 11
			},
			{
				label: '11:00-12:00',
				value: 12
			},
			{
				label: '12:00-13:00',
				value: 13
			},
			{
				label: '13:00-14:00',
				value: 14
			},
			{
				label: '14:00-15:00',
				value: 15
			},
			{
				label: '15:00-16:00',
				value: 16
			},
			{
				label: '16:00-17:00',
				value: 17
			},
			{
				label: '17:00-18:00',
				value: 18
			},
			{
				label: '18:00-19:00',
				value: 19
			},
			{
				label: '19:00-20:00',
				value: 20
			},
		]
	)
	const scroll = (e) => {
		scrollTop.value = e.detail.scrollTop
	}
	//选择具体时间段
	const handleSelectTime = (index, item) => {
		selectedTime.value = index
		selectedTimeLabel.value = item
		popup.value.close('bottom');
		emits('getTime', {
			selectedDay: selectedDay.value,
			selectedDayLabel: selectedDayLabel.value,
			selectedTime: selectedTime.value,
			selectedTimeLabel: selectedTimeLabel.value
		})
	}
	//选择哪天
	const handleSelectDay = (index, item) => {
		selectedDay.value = index
		scrollTop.value = 0
		selectedTime.value = 0
		selectedDayLabel.value = item
	}
	// 打开弹层
	const handleOpen = () => {
		popup.value.open('bottom');

	};
	// 关闭弹层
	const handleCancel = () => {
		popup.value.close('bottom');
	}
	// 暴漏给父组件
	defineExpose({
		handleOpen
	});
</script>

<style lang="scss" scoped src='./getTimePicker.scss'></style>
