<#-- Left side column. contains the sidebar -->
<aside class="main-sidebar">
    <#-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <#-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单项</li>

            <#-- 递归  宏定义 -->
            <#macro bpTree children>
                <#if children?? && children?size gt 0>
                    <#list children as child>
                        <#if child.children?? && child.children?size gt 0>
                            <li class="treeview">
                                <a href="javascript:void(0)">
                                    <i class="${(child.icon=='')?string('fa fa-share',child.icon) }" ></i> <span>${child.displayName}</span>
                                    <span class="pull-right-container">
                                      <i class="fa fa-angle-left pull-right"></i>
                                    </span>
                                </a>
                                <ul class="treeview-menu">
                                    <@bpTree children=child.children />
                                </ul>
                            </li>
                        <#else>
                            <li class="treeview">
                                <a href="${child.url}">
                                    <i class="${(child.icon=='')?string('fa fa-circle-o',child.icon) }"></i> <span>${child.displayName}</span>
                                    <span class="pull-right-container">
                                    </span>
                                </a>
                            </li>
                        </#if>
                    </#list>
                </#if>
            </#macro>
             <!-- 调用宏 生成递归树 -->
             <@bpTree children=treeMenus />
        </ul>

    </section>
    <#-- /.sidebar -->
</aside>