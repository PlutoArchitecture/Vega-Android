###########不需要混淆的Java model，bean###########
#model 不混淆model
-keep class **.model.**{*;}
#bean 不混淆bean
-keep class **.bean.**{*;}
#param 不混淆param
-keep class **.param.**{*;}
#param 不混淆entity
-keep class **.entity.**{*;}
###########不需要混淆的Java model，bean############