<template>
  <div id="app">
    <h1>{{ `基于Element-UI组件改造的树形选择器` }}:</h1>
    <!-- 调用树形下拉框组件 -->
    <!-- 下拉树 -->
    <selectbutton
      :props="props"
      :options="optionData"
      :value="valueId"
      :clearable="isClearable"
      :accordion="isAccordion"
      height="200"
      @getValue="getValue($event)"
    ></selectbutton>
    ID为：{{ valueId }}
  </div>
</template>
