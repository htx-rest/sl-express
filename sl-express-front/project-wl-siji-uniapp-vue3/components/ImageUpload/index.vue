<template>
	<!-- 公用卡片 -->
	<view class="upPicCont">
		<view class="title">{{title}}</view>
		<uni-file-picker 
		v-model="cargoPicture" 
		fileMediatype="image" 
		mode="grid" 
		limit="3"
		@select="select" ></uni-file-picker>
	</view>
	<!-- end -->
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import {
	upload
} from '@/pages/api/index.js'

const cargoPicture = ref([])

const emit = defineEmits(['uploadImage'])

// 获取父组件值、方法
const props = defineProps({
	title:{
		type: String,
		default: '请拍照上传货品照片'
	}, 
	tit:{
		type: String,
		default: ''
	}
});
// 图片上传
async function select(e){
	await upload(e).then(res => {
		if (res.code === 200 && res.data) {
			emit('uploadImage', {key: props.tit, value: res.data})
		} else {
			return uni.showToast({
				title: res.msg,
				duration: 1000,
				icon: 'none'
			});
		}
	}).catch(err => {
		uni.showToast({
			title: '图片上传失败！请联系管理员',
			duration: 1000,
			icon: 'none'
		});
	});
}

onMounted(()=>{
})

</script>
<style lang="scss" scoped>
@import url(@/styles/theme.scss);	
.upPicCont{
	margin-bottom: 32rpx;
	padding-top: 30rpx;
	.title{
		font-weight: 400;
		font-size: var(--font-size-14);
		color: var(--neutral-color-font);
		margin-bottom: 32rpx;
	}
	.file-picker__box-content{
		border:none;
		background: var(--neutral-color-cancel);
	}
}
</style>
