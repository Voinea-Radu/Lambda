import json
import requests

#https://img.shields.io/badge/Up%20To%20Date-3.0.5-green.svg
#https://img.shields.io/badge/Outdated-3.0.5-red.svg

API = "https://repository.lightdream.dev/service/rest/v1/search/assets?sort=version&direction=desc&maven.groupId={group_id}&maven.artifactId={artifact_id}"
PROCESS_QUEUE = "process.queue"
PROJECTS_DATA = "projects.data"

payload = json.loads(open(PROCESS_QUEUE, "r").read())

project = payload["project"]
dependencies = payload["dependencies"]

cache = []

# Create file if does not exist
open(PROJECTS_DATA, "w+") 

raw_report_payload = open(PROJECTS_DATA, "r").read()
if raw_report_payload == "":
    raw_report_payload = "[]"

report_payload = json.loads(raw_report_payload)
report_dependencies = []

def shell(command):
    os.system(command)

def getLatest(group, artifact):
    return requests.get(
        url=API \
        .replace("{group_id}", group) \
        .replace("{artifact_id}", artifact)
    ).json()["items"][0]["maven2"]["version"]

for dependency in dependencies:
    latest =  getLatest(dependency["group"], dependency["artifact"])

    report_dependencies.append({
        "group": dependency["group"],
        "artifact": dependency["artifact"],
        "version": dependency["version"],
        "latest": latest
    })

found = False

for report in report_payload:
    if report["project"]["group"] == project["group"] and report["project"]["artifact"] == project["artifact"]:
        report["project"] = project
        report["dependencies"] = report_dependencies
        found = True

if not found:
    report_payload.append(
        {
            "project": project,
            "dependencies": report_dependencies
        }
    )

open(PROJECTS_DATA, "w+").write(json.dumps(report_payload))