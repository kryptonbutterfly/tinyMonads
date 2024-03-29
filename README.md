# tinyMonads [![Maven Package](https://github.com/kryptonbutterfly/tinyMonads/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/kryptonbutterfly/tinyMonads/actions/workflows/maven-publish.yml)
some Monads for java

Type | Description
:--: | :----------
Opt | Better than null because it's TypeChecked (No NullPointerException at runtime but a compiler error instead).
Failable | A type containing either a result or an exception.
Sum2 | A type containing eiter one type or another one.
Sum3 | A type containing one of 3 types (same as Sum2 but with 3 types instead of 2).
Sum4 | A type containing one of 4 types (same as Sum2 but with 4 types instead of 2).

## Getting the latest release

```xml
<repository>
  <id>github</id>
  <url>https://maven.pkg.github.com/kryptonbutterfly/maven-repo</url>
</repository>
```

```xml
<dependency>
  <groupId>kryptonbutterfly</groupId>
  <artifactId>tiny_monads</artifactId>
  <version>3.0.0</version>
</dependency>
```

## Download

java version | library version | Download
:----------: | :-------------: | --------
18+          | 3.0.0           | [**tiny_monads-3.0.0.jar**](https://github-registry-files.githubusercontent.com/731108692/e1afce00-b653-11ee-8055-1ce835888e8b?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240118%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240118T215046Z&X-Amz-Expires=300&X-Amz-Signature=6a762b747a92e2fccb38fda2dd77567184dfdb6aaa0d3b414f3cbaa8c718c162&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=731108692&response-content-disposition=filename%3Dtiny_monads-3.0.0.jar&response-content-type=application%2Foctet-stream)
18+          | 2.0.0           | [**tiny_monads-2.0.0.jar**](https://github-registry-files.githubusercontent.com/731108692/7c3c4200-9a02-11ee-81ac-20ae686cb0b0?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20231228%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20231228T154413Z&X-Amz-Expires=300&X-Amz-Signature=59e1c7f9b43ee20c1b79f6a56a3b7fe0b8da4c2986b4b2868b452768927ba954&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=731108692&response-content-disposition=filename%3Dtiny_monads-2.0.0.jar&response-content-type=application%2Foctet-stream)
18+          | 1.0.0           | [**Monads.jar**](https://github.com/kryptonbutterfly/tinyMonads/releases/download/v1.0.0/Monads.jar)

