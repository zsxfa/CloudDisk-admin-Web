<template>
	<el-carousel height="690px" loop="false" class="el-carousel">
		<el-carousel-item
			class="carosel-inner"
			v-for="(item, index) in bannerList"
			:key="index"
		>
			<div class="carousel-wrap">
				<div class="carousel-caption">
					<h1 class="bounce">{{ item.title }}</h1>
					<ul>
						<li
							v-for="(descItem, descIndex) in item.descList"
							:key="`desc-${index}-${descIndex}`"
						>
							{{ descItem }}
						</li>
					</ul>
				</div>
				<div class="carousel-img">
					<img :src="item.bannerImg" />
				</div>
			</div>
		</el-carousel-item>
	</el-carousel>
</template>

<script>
export default {
	name: 'Banner',
	data() {
		return {
			bannerList: [
				{
					title: 'Hadoop网络云盘管理系统',
					descList: [
						'文件传输，安全快捷',
						'文件分类，清晰明了',
						'在线解压缩，一触即达',
						'在线预览，方便快捷',
						'回收站，防止文件误删'
					],
					bannerImg: require('_a/images/home/banner/banner1.png')
				}
			]
		}
	},
	methods: {
		// 跳转到网盘页面
		goFile() {
			this.$router.push({ name: 'File', query: { filePath: '/', fileType: 0 } })
		}
	}
}
</script>

<style lang="stylus" scoped>
@import '~_a/styles/varibles.styl';

.el-carousel {
  width: 100%;
  display: block;
  background: linear-gradient(to right,#5f9ea0, #4798b3);

  .carosel-inner {
    width: 100%;

    .carousel-wrap {
      position: relative;
      margin: 0 auto;
      width: 85%;
      height: 100%;

      .carousel-caption {
        position: absolute;
        left: 0;
        top: 120px;
        text-shadow: none;
        max-width: 550px;
        color: #fff;
        text-align: center;

        h1 {
          font-weight: normal;
          margin: 0;
          font-size: 40px;
          animation-delay: 1s;
        }

        ul {
          padding: 25px 0 25px 20px;

          li {
            line-height: 2.2;
            font-size: 20px;
          }
        }

        >>> .el-button {
          border-width: 2px;
          background: transparent;
          color: #fff;

          &:hover {
            border-color: transparent;
            background: $Warning;
          }
        }

        .version-wrapper {
          margin: 0 0 10px;

          .version-item {
            display: inline-block;
            margin: 50px 0 0 18px;
            color: rgba(255, 255, 255, 0.8);

            &:hover {
              text-decoration: underline;
              color: #fff;
            }

            .version-number {
              margin-right: 16px;
            }
          }
        }
      }

      .carousel-img {
        max-width: 580px;
        position: absolute;
        right: 0px;
        top: 0;

        img {
          max-width: 100%;
          vertical-align: middle;
        }
      }
    }
  }
}
</style>
