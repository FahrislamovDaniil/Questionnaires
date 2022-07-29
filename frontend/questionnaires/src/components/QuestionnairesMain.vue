<template>
  <div>

    <div v-if="showAll">

      <div>
        <NavigationTab
        :edit="edit"
        @admin="adminGet" 
        @user="userGet"/>
      </div>
      
      <div v-if="user" class="cardblock">
          <TakeQuestionnaires/>
      </div>

      <div v-if="admin" class="cardblock">
          <MakeQuestionnaires/>
      </div>

    </div>

  </div>
</template>

<script>
import NavigationTab from '@/components/NavigationTab.vue'
import TakeQuestionnaires from '@/components/TakeQuestionnaires.vue'
import MakeQuestionnaires from '@/components/MakeQuestionnaires.vue'

export default {
  name: 'QuestionnairesMain',
  components: {
    NavigationTab,
    TakeQuestionnaires,
    MakeQuestionnaires
},
  data() {
    return{
      showAll: true,
      admin: false,
      user: true,
      edit: false
    }
  },
  mounted() {
    this.edit = true
  },
  unmounted() {
    this.edit = false
  },
  methods: {
    userGet() {
      this.admin = false
      this.user = true
    },
    adminGet() {
      this.admin = true
      this.user = false
    }
  }
}
</script>

<style>
.cardblock {
  padding-bottom: 25px;
  padding-right: 25px;
  padding-left: 25px;
}
.scrollable {
  overflow-y: scroll;
  max-height: 70%;
}
.full {
  width: 60%;
}
</style>