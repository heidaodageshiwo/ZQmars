<header class="main-header">
    <#-- Logo -->
    <a href="/" class="logo">
        <#-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini">
             <div class="image">
                 <img src="${systemInfo.projectIcon }" class="img-circle" alt="logo Image" style="width: 100%;max-width: 32px;height: auto;">
            </div>
        </span>
        <#-- logo for regular state and mobile devices -->
        <span class="logo-lg"><div class="pull-left image">
                 <img src="${systemInfo.projectIcon }" class="img-circle" alt="logo Image" style="width: 100%;max-width: 32px;height: auto;">
            </div>移动终端预警系统</span>
    </a>
    <#-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <#-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </a>
	    <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li><a href="#">服务器时间:<span style="padding-right: 15px;" id="showServTime"></span></a></li>
                <#-- Messages: style can be found in dropdown.less-->
                <li class="dropdown messages-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-envelope-o"></i>
                        <span class="label label-success" id="message-notify-count">${messages.count}</span>
                    </a>
                    <ul class="dropdown-menu" id="message-notify">
                        <li class="header">您有${messages.count}条消息</li>
                        <li>
                            <#-- inner menu: contains the actual data -->
                            <ul class="menu" >
                                <#if messages.messages?? && messages.messages?size gt 0>
                                    <#list messages.messages as message>
                                        <li>
                                            <a href="${message.url}">
                                                <i class="fa fa-envelope-o text-info"></i> ${message.info}
                                            </a>
                                        </li>
                                    </#list>
                                </#if>
                            </ul>
                        </li>
                    </ul>
                </li>
                <#-- Notifications: style can be found in dropdown.less -->
                <li class="dropdown notifications-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-bell-o"></i>
                        <span class="label label-warning" id="alarm-notify-count">${alarms.count}</span>
                    </a>
                    <ul class="dropdown-menu" id="alarm-notify">
                        <li class="header">您有${alarms.count}条告警</li>
                        <li>
                            <#-- inner menu: contains the actual data -->
                            <ul class="menu" >
                                <#if alarms.alarms?? && alarms.alarms?size gt 0>
                                    <#list alarms.alarms as alarm >
                                        <li>
                                            <a href="${alarm.url}">
                                                <#if alarm.type == 1 >
                                                    <i class="fa fa-warning text-yellow"></i> ${alarm.info}
                                                <#else>
                                                    <i class="fa fa-user text-red"></i> ${alarm.info}
                                                </#if>
                                            </a>
                                        </li>
                                    </#list>
                                </#if>
                            </ul>
                        </li>
                    </ul>
                </li>
                <#-- Tasks: style can be found in dropdown.less -->
                <li class="dropdown tasks-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-flag-o"></i>
                        <span class="label label-danger" id="task-notify-count">${tasks.count}</span>
                    </a>
                    <ul class="dropdown-menu" id="task-notify" >
                        <li class="header">您有${tasks.count}条任务</li>
                        <li>
                            <#-- inner menu: contains the actual data -->
                            <ul class="menu" >
                                <#if tasks.tasks?? && tasks.tasks?size gt 0>
                                    <#list tasks.tasks as task >
                                        <li>
                                            <a href="${task.url}">
                                                <h3>
                                                    ${task.info}      <small class="pull-right">${task.progress}%</small>
                                                </h3>
                                                <div data-taskid="${task.id}" class="progress xs">
                                                    <div class="progress-bar progress-bar-green" style="width: ${task.progress}%" role="progressbar" aria-valuenow="${task.progress}" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">${task.progress}% Complete</span>
                                                    </div>
                                                </div>
                                            </a>
                                        </li>
                                    </#list>
                                </#if>
                            </ul>
                        </li>
                    </ul>
                </li>
                <#-- User Account: style can be found in dropdown.less -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="${user.avata}" class="user-image" alt="User Image">
                        <span class="hidden-xs">${user.userName}</span>
                    </a>
                    <ul class="dropdown-menu">
                        <#-- User image -->
                        <li class="user-header">
                            <img src="${user.avata}" class="img-circle" alt="User Image">
                            <p>
                                ${user.userName} - ${user.role}
                                <small>${user.create_time}创建</small>
                            </p>
                        </li>
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a href="/user/profile" class="btn btn-default btn-flat">个人资料</a>
                            </div>
                            <div class="pull-right">
                                <a href="/user/logout" class="btn btn-default btn-flat">登出</a>
                            </div>
                        </li>
                    </ul>
                </li>
                <#-- <li><a href="#">服务器时间:<span style="padding-right: 15px;" id="showServTime"></span></a></li>-->
                <#-- Control Sidebar Toggle Button -->
            </ul>
        </div>
    </nav>
</header>