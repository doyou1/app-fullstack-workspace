# 20221103_歩数計(만보기, pedometer) 開発中止
- AndroidからのStepCounterAPIの機能的な限界で開発中止
- Android에서 StepCounter API의 기능적 한계로 개발 중지
- Development stopped due to functional limitations of StepCounterAPI from Android

# 20221104_家計簿(가계부, AccountBook)_Init
- Reference : [楽な家計簿(편한가계부)](https://play.google.com/store/apps/details?id=com.realbyteapps.moneymanagerfree&hl=ko&gl=US)

# 20221129_家計簿(가계부, AccountBook)_関連UIのサンプリング進行中
- 以前のコミット参考

# 221130_家計簿(가계부, AccountBook)_文書作成
- [Figma Link](https://www.figma.com/file/lbeiHZIfuF97k23i4r77rx/AccountBookUI?node-id=4%3A3&t=9val6cYOjs3XBr31-1)
- サンプリングプロジェクトもどんどん大きくなっていて、全般的な管理もできないし、以前のこととの間違えが増えている。全般的な定義、設計、流れを見てみ台と思う。
- 샘플링 프로젝트도 점점 커지고 있고, 전반적인 관리도 안 되고, 이전의 일과의 오류가 증가하고 있다.전반적인 정의, 설계, 흐름을 살펴보자.
- Sampling projects are getting bigger and bigger, and they can't be managed in a full-scale manner, and the number of mistakes they made is increasing.I think it's a good idea to look at the overall definition, design, and flow.
- 画面遷移、画面でのステータス、データ定義などを確認して開発にも為になるし、設計など見る目も広がると予想。
- 화면 전이, 화면에서의 상태, 데이터 정의등을 확인해 개발에도 도움이 되고, 설계등 보는 눈도 넓어질 것으로 예상.
- It is expected to be useful for development by checking the screen transition, status on the screen, data definition, etc., and expand the view of the design.

# 221130_家計簿(가계부, AccountBook)_文書作成中
- [Figma Link](https://www.figma.com/file/lbeiHZIfuF97k23i4r77rx/AccountBookUI?node-id=4%3A3&t=9val6cYOjs3XBr31-1)
- 楽な家計簿の全ての画面をグループ化し、流れを整理し、画面ごとのデータ定義を予想してみた。
- 편한 가계부의 모든 화면을 그룹화해 흐름을 정리하고 화면별 데이터 정의를 예상해봤다.
- We grouped all the screens in the comfortable household account book, organized the flow, and predicted the definition of data for each screen.

# 221208_家計簿(가계부, AccountBook)_レイアウトに基づく修正
- [Sample Project Link](https://github.com/doyou1/android-example-workspace/tree/master/MultiFragmentInSingleActivitySampling)
- Fragment内のSwipe Eventを通じてActivityのState Changeを反映し、Fragment Refreshする機能、そしてTabLayoutを通じてFragment replaceを進め、Swipe Eventも統一感のある動作を実現するのに大変だった。 様々な方式でサンプリングし、TABLayout、FrameLayout、FrameLayoutにOnTouchListenerを付ける方式を採用した。 他の簡単できれいな方法もあるだろうが、今のところ私が自らコード動作を理解して具現できる最善の方法だ。 人は危機の中で強くなる。
- Fragment내에서의 Swipe Event를 통해 Activity의 State Change 및 반영하고 Fragment Refresh하는 기능, 그리고 그 와중에 TabLayout을 통해 Fragment replace를 진행하며, Swipe Event 역시 통일감 있게 동작하는 Layout을 구현하는 데, 많이 힘들었다. 여러가지 방식으로 샘플링 해보며, TabLayout, FrameLayout, FrameLayout에 OnTouchListener를 다는 방식을 채택했다. 다른 쉽고, 깔끔한 방법도 있겠지만, 지금 내 선에선 내가 스스로 코드 동작을 이해하고 구현할 수 있는 최선의 방식이다. 사람은 위기 속에서 강해진다.
- The function of changing the state of activity and reflecting and refreshing the fragment through the Sweep Event within the Fragment, and in the meantime, the Fragment Replace through TabLayout, and the Sweep Event was also very difficult to implement a layout that operates in a unified manner. By sampling in various ways, we adopted the method of attaching the OnTouch Listener to TabLayout, FrameLayout, and FrameLayout. There are other easy and neat ways, but at this point, it's the best way for me to understand and implement code behavior on my own. A man becomes strong in a crisis.
