(ns status-im2.contexts.quo-preview.main
  (:refer-clojure :exclude [filter])
  (:require
    [quo2.core :as quo]
    [quo2.foundations.colors :as colors]
    [re-frame.core :as re-frame]
    [react-native.core :as rn]
    [status-im2.common.theme.core :as theme]
    [status-im2.contexts.quo-preview.animated-header-list.animated-header-list :as animated-header-list]
    [status-im2.contexts.quo-preview.avatars.account-avatar :as account-avatar]
    [status-im2.contexts.quo-preview.avatars.channel-avatar :as channel-avatar]
    [status-im2.contexts.quo-preview.avatars.group-avatar :as group-avatar]
    [status-im2.contexts.quo-preview.avatars.icon-avatar :as icon-avatar]
    [status-im2.contexts.quo-preview.avatars.user-avatar :as user-avatar]
    [status-im2.contexts.quo-preview.selectors.reactions :as selector-reactions]
    [status-im2.contexts.quo-preview.avatars.wallet-user-avatar :as wallet-user-avatar]
    [status-im2.contexts.quo-preview.banners.banner :as banner]
    [status-im2.contexts.quo-preview.buttons.button :as button]
    [status-im2.contexts.quo-preview.buttons.slide-button :as slide-button]
    [status-im2.contexts.quo-preview.buttons.dynamic-button :as dynamic-button]
    [status-im2.contexts.quo-preview.buttons.predictive-keyboard :as predictive-keyboard]
    [status-im2.contexts.quo-preview.calendar.calendar :as calendar]
    [status-im2.contexts.quo-preview.calendar.calendar-day :as calendar-day]
    [status-im2.contexts.quo-preview.calendar.calendar-year :as calendar-year]
    [status-im2.contexts.quo-preview.browser.browser-input :as browser-input]
    [status-im2.contexts.quo-preview.code.snippet :as code-snippet]
    [status-im2.contexts.quo-preview.graph.wallet-graph :as wallet-graph]
    [status-im2.contexts.quo-preview.colors.color-picker :as color-picker]
    [status-im2.contexts.quo-preview.community.community-card-view :as community-card]
    [status-im2.contexts.quo-preview.community.community-membership-list-view :as
     community-membership-list-view]
    [status-im2.contexts.quo-preview.community.discover-card :as discover-card]
    [status-im2.contexts.quo-preview.community.token-gating :as token-gating]
    [status-im2.contexts.quo-preview.counter.counter :as counter]
    [status-im2.contexts.quo-preview.counter.step :as step]
    [status-im2.contexts.quo-preview.dividers.date :as divider-date]
    [status-im2.contexts.quo-preview.dividers.divider-label :as divider-label]
    [status-im2.contexts.quo-preview.dividers.new-messages :as new-messages]
    [status-im2.contexts.quo-preview.dividers.strength-divider :as strength-divider]
    [status-im2.contexts.quo-preview.drawers.action-drawers :as action-drawers]
    [status-im2.contexts.quo-preview.drawers.documentation-drawers :as documenation-drawers]
    [status-im2.contexts.quo-preview.drawers.drawer-buttons :as drawer-buttons]
    [status-im2.contexts.quo-preview.drawers.permission-drawers :as permission-drawers]
    [status-im2.contexts.quo-preview.dropdowns.dropdown :as dropdown]
    [status-im2.contexts.quo-preview.foundations.shadows :as shadows]
    [status-im2.contexts.quo-preview.info.info-message :as info-message]
    [status-im2.contexts.quo-preview.info.information-box :as information-box]
    [status-im2.contexts.quo-preview.inputs.input :as input]
    [status-im2.contexts.quo-preview.inputs.recovery-phrase-input :as recovery-phrase-input]
    [status-im2.contexts.quo-preview.inputs.profile-input :as profile-input]
    [status-im2.contexts.quo-preview.inputs.search-input :as search-input]
    [status-im2.contexts.quo-preview.inputs.title-input :as title-input]
    [status-im2.contexts.quo-preview.numbered-keyboard.keyboard-key :as keyboard-key]
    [status-im2.contexts.quo-preview.links.url-preview :as url-preview]
    [status-im2.contexts.quo-preview.links.url-preview-list :as url-preview-list]
    [status-im2.contexts.quo-preview.links.link-preview :as link-preview]
    [status-im2.contexts.quo-preview.list-items.channel :as channel]
    [status-im2.contexts.quo-preview.list-items.preview-lists :as preview-lists]
    [status-im2.contexts.quo-preview.list-items.user-list :as user-list]
    [status-im2.contexts.quo-preview.list-items.community-list :as community-list]
    [status-im2.contexts.quo-preview.markdown.text :as text]
    [status-im2.contexts.quo-preview.markdown.list :as markdown-list]
    [status-im2.contexts.quo-preview.messages.author :as messages-author]
    [status-im2.contexts.quo-preview.messages.gap :as messages-gap]
    [status-im2.contexts.quo-preview.messages.system-message :as system-message]
    [status-im2.contexts.quo-preview.navigation.bottom-nav-tab :as bottom-nav-tab]
    [status-im2.contexts.quo-preview.navigation.floating-shell-button :as floating-shell-button]
    [status-im2.contexts.quo-preview.navigation.page-nav :as page-nav]
    [status-im2.contexts.quo-preview.navigation.top-nav :as top-nav]
    [status-im2.contexts.quo-preview.notifications.activity-logs :as activity-logs]
    [status-im2.contexts.quo-preview.notifications.activity-logs-photos :as activity-logs-photos]
    [status-im2.contexts.quo-preview.notifications.notification :as notification]
    [status-im2.contexts.quo-preview.notifications.toast :as toast]
    [status-im2.contexts.quo-preview.onboarding.small-option-card :as small-option-card]
    [status-im2.contexts.quo-preview.password.tips :as tips]
    [status-im2.contexts.quo-preview.profile.collectible :as collectible]
    [status-im2.contexts.quo-preview.profile.profile-card :as profile-card]
    [status-im2.contexts.quo-preview.profile.select-profile :as select-profile]
    [status-im2.contexts.quo-preview.reactions.react :as react]
    [status-im2.contexts.quo-preview.record-audio.record-audio :as record-audio]
    [status-im2.contexts.quo-preview.selectors.disclaimer :as disclaimer]
    [status-im2.contexts.quo-preview.selectors.filter :as filter]
    [status-im2.contexts.quo-preview.selectors.selectors :as selectors]
    [status-im2.contexts.quo-preview.settings.accounts :as accounts]
    [status-im2.contexts.quo-preview.settings.settings-list :as settings-list]
    [status-im2.contexts.quo-preview.settings.privacy-option :as privacy-option]
    [status-im2.contexts.quo-preview.settings.reorder-item :as reorder-item]
    [status-im2.contexts.quo-preview.settings.category :as category]
    [status-im2.contexts.quo-preview.share.qr-code :as qr-code]
    [status-im2.contexts.quo-preview.share.share-qr-code :as share-qr-code]
    [status-im2.contexts.quo-preview.switcher.switcher-cards :as switcher-cards]
    [status-im2.contexts.quo-preview.tabs.account-selector :as account-selector]
    [status-im2.contexts.quo-preview.tabs.segmented-tab :as segmented]
    [status-im2.contexts.quo-preview.tabs.tabs :as tabs]
    [status-im2.contexts.quo-preview.empty-state.empty-state :as empty-state]
    [status-im2.contexts.quo-preview.tags.context-tags :as context-tags]
    [status-im2.contexts.quo-preview.tags.permission-tag :as permission-tag]
    [status-im2.contexts.quo-preview.tags.status-tags :as status-tags]
    [status-im2.contexts.quo-preview.tags.tags :as tags]
    [status-im2.contexts.quo-preview.tags.token-tag :as token-tag]
    [status-im2.contexts.quo-preview.title.title :as title]
    [status-im2.contexts.quo-preview.keycard.keycard :as keycard]
    [status-im2.contexts.quo-preview.loaders.skeleton :as skeleton]
    [status-im2.contexts.quo-preview.community.channel-actions :as channel-actions]
    [status-im2.contexts.quo-preview.gradient.gradient-cover :as gradient-cover]
    [status-im2.contexts.quo-preview.wallet.network-amount :as network-amount]
    [status-im2.contexts.quo-preview.wallet.network-bridge :as network-bridge]
    [status-im2.contexts.quo-preview.wallet.account-card :as account-card]
    [status-im2.contexts.quo-preview.wallet.token-input :as token-input]))

(def screens-categories
  {:foundations       [{:name      :shadows
                        :options   {:topBar {:visible true}}
                        :component shadows/preview-shadows}]
   :animated-list     [{:name      :animated-header-list
                        :options   {:topBar {:visible false}}
                        :component animated-header-list/mock-screen}]
   :avatar            [{:name      :group-avatar
                        :options   {:topBar {:visible true}}
                        :component group-avatar/preview-group-avatar}
                       {:name      :icon-avatar
                        :options   {:topBar {:visible true}}
                        :component icon-avatar/preview-icon-avatar}
                       {:name      :user-avatar
                        :options   {:topBar {:visible true}}
                        :component user-avatar/preview-user-avatar}
                       {:name      :wallet-user-avatar
                        :options   {:topBar {:visible true}}
                        :component wallet-user-avatar/preview-wallet-user-avatar}
                       {:name      :channel-avatar
                        :options   {:topBar {:visible true}}
                        :component channel-avatar/preview-channel-avatar}
                       {:name      :account-avatar
                        :options   {:topBar {:visible true}}
                        :component account-avatar/preview-account-avatar}]
   :banner            [{:name      :banner
                        :options   {:topBar {:visible true}}
                        :component banner/preview-banner}]
   :buttons           [{:name      :button
                        :options   {:topBar {:visible true}}
                        :component button/preview-button}
                       {:name      :dynamic-button
                        :options   {:topBar {:visible true}}
                        :component dynamic-button/preview-dynamic-button}
                       {:name      :slide-button
                        :options   {:topBar {:visible true}}
                        :component slide-button/preview-slide-button}
                       {:name      :predictive-keyboard
                        :options   {:topBar {:visible true}}
                        :component predictive-keyboard/preview-predictive-keyboard}]
   :browser           [{:name      :browser-input
                        :options   {:topBar {:visible false}}
                        :component browser-input/preview-browser-input}]
   :calendar          [{:name      :calendar
                        :options   {:topBar {:visible true}}
                        :component calendar/preview-calendar}
                       {:name      :calendar-day
                        :options   {:topBar {:visible true}}
                        :component calendar-day/preview-calendar-day}
                       {:name      :calendar-year
                        :options   {:topBar {:visible true}}
                        :component calendar-year/preview-calendar-year}]
   :code              [{:name      :snippet
                        :options   {:topBar {:visible true}}
                        :component code-snippet/preview-code-snippet}]
   :colors            [{:name      :color-picker
                        :options   {:topBar {:visible true}}
                        :component color-picker/preview-color-picker}]
   :community         [{:name      :community-card-view
                        :options   {:topBar {:visible true}}
                        :component community-card/preview-community-card}
                       {:name      :community-membership-list-view
                        :options   {:topBar {:visible true}}
                        :component community-membership-list-view/preview-community-list-view}
                       {:name      :discover-card
                        :options   {:topBar {:visible true}}
                        :component discover-card/preview-discoverd-card}
                       {:name      :token-gating
                        :options   {:insets {:bottom? true}
                                    :topBar {:visible true}}
                        :component token-gating/preview-token-gating}
                       {:name      :channel-actions
                        :options   {:insets {:bottom? true}
                                    :topBar {:visible true}}
                        :component channel-actions/preview-channel-actions}]
   :counter           [{:name      :counter
                        :options   {:topBar {:visible true}}
                        :component counter/preview-counter}
                       {:name      :step
                        :options   {:topBar {:visible true}}
                        :component step/preview-step}]
   :dividers          [{:name      :divider-label
                        :options   {:topBar {:visible true}}
                        :component divider-label/preview-divider-label}
                       {:name      :new-messages
                        :options   {:topBar {:visible true}}
                        :component new-messages/preview-new-messages}
                       {:name      :divider-date
                        :options   {:topBar {:visible true}}
                        :component divider-date/preview-divider-date}
                       {:name      :strength-divider
                        :options   {:topBar {:visible true}}
                        :component strength-divider/preview-strength-divider}]
   :drawers           [{:name      :action-drawers
                        :options   {:topBar {:visible true}}
                        :component action-drawers/preview-action-drawers}
                       {:name      :documentation-drawer
                        :insets    {:top false}
                        :component documenation-drawers/preview-documenation-drawers}
                       {:name      :drawer-buttons
                        :options   {:topBar {:visible true}}
                        :component drawer-buttons/preview-drawer-buttons}
                       {:name      :permission-drawers
                        :options   {:topBar {:visible true}}
                        :component permission-drawers/preview-permission-drawers}]
   :dropdowns         [{:name      :dropdown
                        :options   {:topBar {:visible true}}
                        :component dropdown/preview-dropdown}]
   :empty-state       [{:name      :empty-state
                        :options   {:topBar {:visible true}}
                        :component empty-state/preview-empty-state}]
   :gradient          [{:name      :gradient-cover
                        :options   {:topBar {:visible true}}
                        :component gradient-cover/preview-gradient-cover}]
   :graph             [{:name      :wallet-graph
                        :options   {:topBar {:visible true}}
                        :component wallet-graph/preview-wallet-graph}]
   :info              [{:name      :info-message
                        :options   {:topBar {:visible true}}
                        :component info-message/preview-info-message}
                       {:name      :information-box
                        :options   {:topBar {:visible true}}
                        :component information-box/preview-information-box}]
   :inputs            [{:name      :input
                        :options   {:topBar {:visible true}}
                        :component input/preview-input}
                       {:name      :profile-input
                        :options   {:topBar {:visible true}}
                        :component profile-input/preview-profile-input}
                       {:name      :recovery-phrase-input
                        :options   {:topBar {:visible true}}
                        :component recovery-phrase-input/preview-recovery-phrase-input}
                       {:name      :search-input
                        :options   {:topBar {:visible true}}
                        :component search-input/preview-search-input}
                       {:name      :title-input
                        :options   {:topBar {:visible true}}
                        :component title-input/preview-title-input}]
   :numbered-keyboard [{:name      :keyboard-key
                        :options   {:insets {:top? true}
                                    :topBar {:visible true}}
                        :component keyboard-key/preview-keyboard-key}]
   :links             [{:name      :url-preview
                        :options   {:insets {:top? true}
                                    :topBar {:visible true}}
                        :component url-preview/preview}
                       {:name      :url-preview-list
                        :options   {:insets {:top? true}
                                    :topBar {:visible true}}
                        :component url-preview-list/preview}
                       {:name      :link-preview
                        :options   {:insets {:top? true}
                                    :topBar {:visible true}}
                        :component link-preview/preview}]
   :list-items        [{:name      :channel
                        :options   {:topBar {:visible true}}
                        :component channel/preview-channel}
                       {:name      :community-list
                        :options   {:insets {:top? true}
                                    :topBar {:visible true}}
                        :component community-list/preview}
                       {:name      :preview-lists
                        :options   {:topBar {:visible true}}
                        :component preview-lists/preview-preview-lists}
                       {:name      :user-list
                        :options   {:topBar {:visible true}}
                        :component user-list/preview-user-list}]
   :loaders           [{:name      :skeleton
                        :options   {:topBar {:visible true}}
                        :component skeleton/preview-skeleton}]
   :markdown          [{:name      :texts
                        :options   {:topBar {:visible true}}
                        :component text/preview-text}
                       {:name      :markdown-list
                        :options   {:topBar {:visible true}}
                        :component markdown-list/preview-markdown-list}]
   :messages          [{:name      :gap
                        :options   {:topBar {:visible true}}
                        :component messages-gap/preview-messages-gap}
                       {:name      :system-messages
                        :options   {:topBar {:visible true}}
                        :component system-message/preview-system-message}
                       {:name      :author
                        :options   {:topBar {:visible true}}
                        :component messages-author/preview-author}]
   :navigation        [{:name      :bottom-nav-tab
                        :options   {:topBar {:visible true}}
                        :component bottom-nav-tab/preview-bottom-nav-tab}
                       {:name      :top-nav
                        :options   {:topBar {:visible true}}
                        :component top-nav/preview-top-nav}
                       {:name      :page-nav
                        :options   {:topBar {:visible true}}
                        :component page-nav/preview-page-nav}
                       {:name      :floating-shell-button
                        :options   {:topBar {:visible true}}
                        :component floating-shell-button/preview-floating-shell-button}]
   :notifications     [{:name      :activity-logs
                        :options   {:topBar {:visible true}}
                        :component activity-logs/preview-activity-logs}
                       {:name      :activity-logs-photos
                        :options   {:topBar {:visible true}}
                        :component activity-logs-photos/preview-activity-logs-photos}
                       {:name      :toast
                        :options   {:topBar {:visible true}}
                        :component toast/preview-toasts}
                       {:name      :notification
                        :options   {:topBar {:visible true}}
                        :component notification/preview-notification}]
   :onboarding        [{:name      :small-option-card
                        :options   {:topBar {:visible true}}
                        :component small-option-card/preview-small-option-card}]
   :password          [{:name      :tips
                        :options   {:topBar {:visible true}}
                        :component tips/preview-tips}]
   :profile           [{:name      :profile-card
                        :options   {:topBar {:visible true}}
                        :component profile-card/preview-profile-card}
                       {:name      :collectible
                        :options   {:topBar {:visible true}}
                        :component collectible/preview-collectible}
                       {:name      :select-profile
                        :options   {:topBar {:visible true}}
                        :component select-profile/preview-select-profile}]
   :reactions         [{:name      :react
                        :options   {:topBar {:visible true}}
                        :component react/preview-react}]
   :record-audio      [{:name      :record-audio
                        :options   {:topBar {:visible true}}
                        :component record-audio/preview-record-audio}]
   :switcher          [{:name      :switcher-cards
                        :options   {:topBar {:visible true}}
                        :component switcher-cards/preview-switcher-cards}]
   :selectors         [{:name      :disclaimer
                        :options   {:topBar {:visible true}}
                        :component disclaimer/preview-disclaimer}
                       {:name      :filter
                        :options   {:topBar {:visible true}}
                        :component filter/preview}
                       {:name      :selectors
                        :options   {:topBar {:visible true}}
                        :component selectors/preview-selectors}
                       {:name      :select-reactions
                        :options   {:topBar {:visible true}}
                        :component selector-reactions/preview}]
   :settings          [{:name      :privacy-option
                        :options   {:topBar {:visible true}}
                        :component privacy-option/preview-options}
                       {:name      :accounts
                        :options   {:topBar {:visible true}}
                        :component accounts/preview-accounts}
                       {:name      :settings-list
                        :options   {:topBar {:visible true}}
                        :component settings-list/preview-settings-list}
                       {:name      :reorder-item
                        :options   {:topBar {:visible true}}
                        :component reorder-item/preview-reorder-item}
                       {:name      :category
                        :options   {:topBar {:visible true}}
                        :component category/preview}]
   :share             [{:name      :qr-code
                        :options   {:topBar {:visible true}}
                        :component qr-code/preview-qr-code}
                       {:name      :share-qr-code
                        :options   {:topBar {:visible true}}
                        :component share-qr-code/preview-share-qr-code}]
   :tabs              [{:name      :segmented
                        :options   {:topBar {:visible true}}
                        :component segmented/preview-segmented}
                       {:name      :tabs
                        :options   {:topBar {:visible true}}
                        :component tabs/preview-tabs}
                       {:name      :account-selector
                        :options   {:topBar {:visible true}}
                        :component account-selector/preview-this}]
   :tags              [{:name      :context-tags
                        :options   {:topBar {:visible true}}
                        :component context-tags/preview-context-tags}
                       {:name      :tags
                        :options   {:topBar {:visible true}}
                        :component tags/preview-tags}
                       {:name      :permission-tag
                        :options   {:topBar {:visible true}}
                        :component permission-tag/preview-permission-tag}
                       {:name      :status-tags
                        :options   {:topBar {:visible true}}
                        :component status-tags/preview-status-tags}
                       {:name      :token-tag
                        :options   {:topBar {:visible true}}
                        :component token-tag/preview-token-tag}]
   :text-combinations [{:name      :title
                        :options   {:topBar {:visible true}}
                        :component title/preview-title}]
   :wallet            [{:name      :account-card
                        :options   {:topBar {:visible true}}
                        :component account-card/preview-account-card}
                       {:name      :network-amount
                        :options   {:topBar {:visible true}}
                        :component network-amount/preview}
                       {:name      :network-bridge
                        :options   {:topBar {:visible true}}
                        :component network-bridge/preview}
                       {:name      :token-input
                        :options   {:topBar {:visible true}}
                        :component token-input/preview}]
   :keycard           [{:name      :keycard-component
                        :options   {:topBar {:visible true}}
                        :component keycard/preview-keycard}]})

(def screens (flatten (map val screens-categories)))

(defn theme-switcher
  []
  [rn/view
   {:style {:flex-direction  :row
            :margin-vertical 8}}
   [quo/button {:on-press #(theme/set-theme :light)} "Set light theme"]
   [quo/button {:on-press #(theme/set-theme :dark)} "Set dark theme"]])

(defn main-screen
  []
  (fn []
    [rn/scroll-view
     {:flex               1
      :padding-bottom     8
      :padding-horizontal 16
      :background-color   (colors/theme-colors colors/white colors/neutral-90)}
     [theme-switcher]
     [quo/text {:size :heading-1} "Preview Quo2 Components"]
     [rn/view
      (map (fn [category]
             ^{:key (get category 0)}
             [rn/view {:style {:margin-vertical 8}}
              [quo/text
               {:weight :semi-bold
                :size   :heading-2}
               (clojure.core/name (key category))]
              (for [{:keys [name]} (val category)]
                ^{:key name}
                [quo/button
                 {:container-style {:margin-vertical 8}
                  :on-press        #(re-frame/dispatch [:navigate-to name])}
                 (clojure.core/name name)])])
           (sort screens-categories))]]))

(def main-screens
  [{:name      :quo2-preview
    :options   {:topBar {:visible true}}
    :component main-screen}])
