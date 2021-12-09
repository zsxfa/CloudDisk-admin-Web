<template>
	<div class="home-swapper">
		<!-- Banner图 -->
		<Banner></Banner>
	</div>
</template>

<script>
import Banner from '_c/home/Banner.vue'

export default {
	name: 'Home',
	components: {
		Banner
	}
}
</script>

<style lang="stylus" scoped>
.home-swapper {
  width: 100% !important;
  flex-direction column
}
</style>
