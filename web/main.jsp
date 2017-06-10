<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>xx</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/reset.css">
    <script type="text/javascript" src="js/jquery-3.1.1.js"></script>
    <script type="text/javascript" src="js/index.js"></script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" id="menu-toggle" class=" navbar-btn collapsed"
                    data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">


            <form class="navbar-form navbar-left" action=" " method=" ">
                <div class="form-group">
                    <input type="text" class="form-control search" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>

            <ul class="nav navbar-nav navbar-right">
                <li>
                    <button class="btn btn-default" id="delete"
                            style="display:none; margin:10px 0px;">删除
                    </button>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-haspopup="true" aria-expanded="false">xxx <span
                            class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="wraper">

    <div class="asider-menu-default">
        <div class="menu-list ">
            <div class="menu-item ">
                <a href="#"><span class="glyphicon glyphicon-list-alt"></span><span>联系人</span></a>
            </div>
            <div class="menu-item">
                <a href="#"><span class="glyphicon glyphicon-tags"></span><span>联系人</span></a>
            </div>
            <div class="menu-item">
                <a href="#"><span class="glyphicon glyphicon-retweet"></span><span>联系人</span></a>
            </div>
            <hr>
        </div>
    </div>
    <div class="datalist-default">

        <div class="tel-list">
            <div class="list-item list-item1">
                <div class="tel-listIcon">
                    <img src="https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5MN3ESuFcwTdJhvSQWNPBg6QIhgKABD___________8BGJ7Szfn______wE/s36-p-k-rw-no/photo.jpg"
                         alt="icon">
                    <div class="check">
                        <!--  <span class="checkbox">&nbsp</span> -->
                        <input type="checkbox">
                    </div>
                </div>
                <div class="tel-message">
                    <span class="phoneNum">1832695545</span>
                    <span class="email">pengshang@gmail.com</span>
                </div>
            </div>

            <div class="list-item list-item1">
                <div class="tel-listIcon">
                    <img src="https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5MN3ESuFcwTdJhvSQWNPBg6QIhgKABD___________8BGJ7Szfn______wE/s36-p-k-rw-no/photo.jpg"
                         alt="icon">
                    <div class="check">
                        <!--  <span class="checkbox">&nbsp</span> -->
                        <input type="checkbox">
                    </div>
                </div>
                <div class="tel-message">
                    <span class="phoneNum">1832695545</span>
                    <span class="email">pengshang@gmail.com</span>
                </div>
            </div>

            <div class="list-item list-item1">
                <div class="tel-listIcon">
                    <img src="https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5MN3ESuFcwTdJhvSQWNPBg6QIhgKABD___________8BGJ7Szfn______wE/s36-p-k-rw-no/photo.jpg"
                         alt="icon">
                    <div class="check">
                        <!--  <span class="checkbox">&nbsp</span> -->
                        <input type="checkbox">
                    </div>
                </div>
                <div class="tel-message">
                    <span class="phoneNum">1832695545</span>
                    <span class="email">pengshang@gmail.com</span>
                </div>
            </div>

        </div>
    </div>

</div>
</body>
</html>