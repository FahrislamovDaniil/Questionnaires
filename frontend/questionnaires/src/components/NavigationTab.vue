<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-white">
        <div class="container-fluid">
            <a @click="$router.go(0)" class="navbar-brand pointer">Тест-анкета</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item pointer">
                <a @click="$router.push('/tests'); $emit('user')" class="nav-link active pointer" aria-current="page">Анкеты</a>
                </li>
                <li v-if="edit" class="nav-item">
                <a @click="$router.push('/tests'); $emit('admin')" class="nav-link active pointer" aria-current="page">Создать анкету</a>
                </li>
                <li @click="$router.push('/attempts')" class="nav-item pointer">
                <a class="nav-link active" aria-current="page">Посмотреть результаты</a>
                </li>
                <li @click="$router.push('/')" class="nav-item pointer">
                <a class="nav-link active" aria-current="page">Сменить пользователя</a>
                </li>
            </ul>
            </div>
        </div>
    </nav>
</template>

<script>

export default {
  name: 'NavigationTab',
  props: ['edit'],
  components: {},
  data() {
    return {}
  },
  methods: {}
}
</script>
<style>
.pointer {
    cursor: pointer;
}
</style>