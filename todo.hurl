GET http://localost:8080/todo
HTTP 200
# The following assert are equivalent:
[Asserts]
jsonpath "$.slideshow.title" == "A beautiful ✈!"