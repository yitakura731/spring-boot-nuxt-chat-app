<template>
  <div class="parent h-100 w-100">
    <div class="child d-flex justify-content-center align-items-center">
      <div class="d-flex justify-content-center">
        <b-spinner style="width: 6rem; height: 6rem;" label="Large Spinner" />
      </div>
    </div>
  </div>
</template>

<style>
.parent {
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(10, 10, 10, 0.5);
  z-index: 999;
  transition: all 0.5s ease;
}
.child {
  user-select: none;
  width: 60px;
  height: 60px;
  transition: all 0.3s ease;
}
</style>
