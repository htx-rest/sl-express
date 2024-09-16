<template>
	<nav-bar title='地址簿' :handleToLink="handleToLink"></nav-bar>
	<view class="address" v-if="netStatus">
		
		<view class="seachBox" >
			<uni-search-bar cancelButton='none' placeholder="请输入姓名/手机号" v-model="searchValue" @input="input"
				@cancel="cancel" @clear="clear">
				<template v-slot:searchIcon>
					<view class="search-icon"></view>
				</template>
			</uni-search-bar>
		</view>
		<view class="list-container">
			<scroll-view v-if="list.data.length > 0" scroll-y :style="scrollHeight" lower-threshold="30"
				@scrolltolower="LoadMoreCustomers">
				<!-- 列表内容 -->
				<view v-for='(item,index) in list.data'
					@click.stop.prevent="isManage?checkbox(index,item):handleToAddressInfo(item.id)" :key='index'
					class="address-list" :class="isManage?'isChecked':''">
					<view class="checkbox" v-if="isManage">
						<view class="checkRadio">
							<radio style="transform: scale(0.77)" color="#F23C24" :value="String(index)"
								:checked="item.selected" />
						</view>
					</view>
					<view class="address-item" :class="isManage?'isChecked':''">
						<view class="address-name">{{item.name}}</view>
						<view class="address-phone">{{item.phoneNumber}}</view>
						<view class="address-address">
							{{item.province? item.province.name+item.city.name+ item.county.name+item.address:''}}
						</view>
						<view class="line"></view>
						<view class="address-item-footer" :class="type === 'get'?'active':''">
							<view class="save-address" v-if="type !== 'get'"
								@click.stop="handleSaveToDefaultAddress(item.id,item.isDefault)">
								<view class="checkbox" :class="{active:Boolean(item.isDefault)}"></view>
								<view class="label"> 默认寄件地址</view>
							</view>
							<view class="right-box" :class="!isManage?'':'active'">
								<view class="edit-btn" @click.stop="handleEditAddress(item)">
									<icon></icon>
									<text>编辑</text>
								</view>
								<view class="delete-btn" @click.stop="handledDelete(item.id)">
									<icon></icon>
									<text>删除</text>
								</view>
							</view>
						</view>
					</view>
				</view>
				<!-- 加载底部 -->
				<uni-load-more :status="status" />
			</scroll-view>
			<!-- 空页面 -->
			<view v-else class="empty-box">
				<image src='../../static/address-empty.png'></image>
				<view>{{searchValue?'没有搜索到相关条件的地址':'暂无数据'}}</view>
			</view>
			<!-- 全选操作栏 -->
			<view class="all-select" v-if="isManage">
				<view class="checkbox" @click="selectAll">
					<view class="checkRadio">
						<radio style="transform: scale(0.77)" :checked="isAllSelect" color="#F23C24" />
					</view>
				</view>
				<view>全选</view>
				<!-- <uni-radio>123</uni-radio> -->
			</view>

			<!-- 底部操作栏 -->
			<view class="address-footer">
				<view class="manage-btn" v-if="list.data.length > 0" @click="handleEdit">{{isManage?'完成':'管理'}}</view>
				<view class="addAddress-btn" :class="(isManage&&deleteIds.data.length)|| !isManage?'active':''"
					@click="handleDeleteOrAdd">{{isManage?'删除':'新增地址'}}</view>
			</view>
		</view>
	</view>
	<!-- 断网显示的页面 -->
	<net-fail v-else :handleToRefresh="handleToRefresh"></net-fail>

	<!-- 删除确认对话框 -->
	<uni-popup ref="popup" type="dialog" class='address-popup'>
		<uni-popup-dialog mode="base" :content="isManage?'确定是否删除所选地址？':'确定是否删除此条地址？'" :animation='false'
			:before-close="true" @close="close" @confirm="confirm">

		</uni-popup-dialog>
	</uni-popup>

</template>

<script setup>
	import {
		ref,
		reactive,
		onMounted,
		watch,
		nextTick
	} from 'vue';
	import {
		onPullDownRefresh
	} from '@dcloudio/uni-app';
	import {
		getAddressList,
		deleteAddress,
		editAddress
	} from '@/pages/api/address.js'
	import {
		onLoad,
		onShow
	} from '@dcloudio/uni-app';
	let scrollHeight = ref('')
	let searchValue = ref('')
	let popup = ref(null)
	let isAllSelect = ref(false)
	let isManage = ref(false)
	let emptyData = ref('暂无数据');
	let deleteIds = reactive({
		data: [] //删除的地址id集合
	})
	let list = reactive({
		data: [] //列表数据
	})
	let pageInfo = reactive({
		page: 1,
		pageSize: 10
	})
	let status = ref('more') //加载状态
	let height = ref('670px')
	let type = ref('')
	let isFromAddress = ref('')
	let netStatus = ref(true)
	onLoad((options) => {
		type.value = options.type
		isFromAddress.value = options.isFromAddress
		uni.getSystemInfo({
			success: (res) => {
				scrollHeight.value = 'height:' + (res.screenHeight - 190) + 'px'
			}
		})
	})
	onMounted(() => {
		getList()
	})

	onPullDownRefresh(() => {
		pageInfo.page = 1
		getList()
	})
	const handleToRefresh = ()=>{
		pageInfo.page = 1
		getList()
	}
	const input = (e) => {
		searchValue.value = e
		pageInfo.page = 1
		getList()
	}
	const clear = () => {

	}
	//设置是否为默认寄件地址
	const handleSaveToDefaultAddress = (id, isDefault) => {
		if (isManage.value) {
			return
		}
		editAddress({
			id,
			isDefault: isDefault === 1 ? 0 : 1
		}).then((res) => {
			pageInfo.page = 1
			getList()
			if (res.code === 200) {
				uni.showToast({
					title: '修改成功',
					icon: 'none',
					duration: 1000,
					type: 'success'
				})
			} else {
				uni.showToast({
					title: '修改成功',
					icon: 'none',
					duration: 1000,
					type: 'error'
				})
			}

		}).catch(()=>{
			uni.showToast({
				title: '网络异常',
				duration: 2000,
				icon: 'none'
			});
		})
	}
	//返回上一级防止是最后一级报错
	const handleToLink = () => {
		if (type.value !== 'address') {
			uni.navigateBack()
		} else {
			uni.switchTab({
				url: '/pages/my/index'
			})
		}

	}
	//跳转回寄件收件选择地址
	const handleToAddressInfo = (id) => {
		if (type.value === 'address') {
			return
		} else if (type.value === 'get') {
			if (uni.getStorageSync('sendId') === id) return uni.showToast({
				title: '寄件地址和收件地址不能选择同一个',
				icon: 'none',
				duration: 1000
			})
		} else {
			if (uni.getStorageSync('getId') === id) return uni.showToast({
				title: '寄件地址和收件地址不能选择同一个',
				icon: 'none',
				duration: 1000
			})
		}

		// if ([uni.getStorageSync('sendId'), uni.getStorageSync('getId')].includes(id)) {

		// }
		uni.redirectTo({
			url: '/pages/express-delivery/index?type=' + type.value + '&editOrAdd=edit&id=' + id +
				'&isFromAddress=true'
		});
	}
	//批量删除或者新增地址
	const handleDeleteOrAdd = () => {
		if (isManage.value) {
			if (deleteIds.data.length) popup.value.open()
		} else {
			uni.redirectTo({
				url: '/subPages/address-info/index?isFromAddress=' +isFromAddress.value +'&editOrAdd=add&type=' + type.value
			});
		}
	}
	//编辑地址
	const handleEditAddress = (item) => {
		if (isManage.value) {
			return
		}
		uni.redirectTo({
			url: '/subPages/address-info/index?id=' + item.id + '&isFromAddress='+isFromAddress.value + '&editOrAdd=edit' +
				'&type=' +type.value + '&isDefault=' + item.isDefault
		});
	}
	//获取列表数据
	const getList = (flag) => {
		status.value = 'loading'
		getAddressList({
			page: pageInfo.page,
			pageSize: pageInfo.pageSize,
			keyword: searchValue.value
		}).then((res) => {
			status.value = res.data.items.length < 10 ? 'no-more' : 'more'
			//用于区分是一次进页面记载数据还是后续上拉加载
			if (flag === 'topPull') {
				list.data = list.data.concat(res.data.items)
			} else {
				list.data = res.data.items
			}
			uni.stopPullDownRefresh();
			netStatus.value = true
		}).catch((err) => {
			uni.showToast({
				title: '网络异常',
				duration: 2000,
				icon: 'none'
			});
			netStatus.value = false
		})
	}
	//上拉加载
	const LoadMoreCustomers = () => {
		pageInfo.page = pageInfo.page + 1
		if (status.value === 'no-more') {
			return
		}
		getList('topPull')
	}
	//全选
	const handledAllSelect = () => {
		isAllSelect.value = !isAllSelect.value
	}
	//管理地址簿
	const handleEdit = () => {
		isManage.value = !isManage.value
	}

	//删除
	const handledDelete = (id) => {
		if (isManage.value) {
			return
		}
		popup.value.open()
		deleteIds.data.push(id)
	}

	//关闭删除确认提示框
	const close = () => {
		popup.value.close()
		deleteIds.data = []
	}

	//确认删除
	const confirm = () => {
		popup.value.close()
		deleteAddress(deleteIds.data).then((res) => {
			pageInfo.page = 1
			pageInfo.pageSize = 10
			getList()
			uni.showToast({
				title: '删除成功',
				icon: 'success',
				duration: 1000
			})
			isManage.value = false
		}).catch(()=>{
			uni.showToast({
				title: '网络异常',
				duration: 2000,
				icon: 'none'
			});
		})
	}

	//选中
	const checkbox = (index, item) => {
		list.data[index].selected = !list.data[index].selected
		deleteIds.data = list.data.filter(item => item.selected).map(item => item.id)
	}
	//全选
	const selectAll = () => {
		isAllSelect.value = !isAllSelect.value
		list.data = list.data.map((item) => {
			return Object.assign({}, item, {
				selected: isAllSelect.value
			})
		})
		deleteIds.data = list.data.filter(item => item.selected).map(item => item.id)
	}
</script>

<style src="./index.scss" lang="scss" scoped></style>
