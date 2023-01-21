import os
import json

TRACKING_GROUPS=["dev.lightdream", "com.pokeninjas"]
TMP_FOLDER = "tmp"
DEPENDENCIES_OUTPUT = "dependencies.output"
ARTIFACT_OUTPUT = "artifact.output"
VERSION_OUTPUT = "version.output"
GROUP = "group.output"
WORKING_DIR = "run"
PROCESS_DATA = "process.data"


def shell(command):
    os.system(command)

def createFolder(folder):
    if not os.path.exists(folder):
        os.makedirs(folder)

createFolder(f"{WORKING_DIR}/{TMP_FOLDER}")

shell(f"./gradlew dependencies --configuration runtimeClasspath > {WORKING_DIR}/{TMP_FOLDER}/{DEPENDENCIES_OUTPUT}")
shell(f"gradle properties -q | grep \"^name:\" | awk '{{print $2}}' > {WORKING_DIR}/{TMP_FOLDER}/{ARTIFACT_OUTPUT}")
shell(f"gradle properties -q | grep \"^version:\" | awk '{{print $2}}' > {WORKING_DIR}/{TMP_FOLDER}/{VERSION_OUTPUT}")
shell(f"gradle properties -q | grep \"^group:\" | awk '{{print $2}}' > {WORKING_DIR}/{TMP_FOLDER}/{VERSION_OUTPUT}")

raw_dependencies = open(f"{WORKING_DIR}/{TMP_FOLDER}/{DEPENDENCIES_OUTPUT}", "r").read()

project_artifact = open(f"{WORKING_DIR}/{TMP_FOLDER}/{ARTIFACT_OUTPUT}", "r").read().replace("\n", "")
project_version = open(f"{WORKING_DIR}/{TMP_FOLDER}/{VERSION_OUTPUT}", "r").read().replace("\n", "")
project_group = open(f"{WORKING_DIR}/{TMP_FOLDER}/{VERSION_OUTPUT}", "r").read().replace("\n", "")

print("Artifact: " + project_artifact)
print("Version: " + project_version)
print("Group: " + project_group)

project = {
    "group": project_group,
    "artifact": project_artifact,
    "version": project_version
}

dependencies_lines = raw_dependencies.split("\n")

dependencies_entries = []

for line in dependencies_lines:
    for group in TRACKING_GROUPS:
        if group in line:
            dependencies_entries.append(line)
            break

dependencies = []

for raw_entry in dependencies_entries:
    entry = raw_entry.replace(" ", "")
    print(entry)
    group = ""

    for group_entry in TRACKING_GROUPS:
        if group_entry in entry.split(":")[0]:
            group = group_entry

    artifact = entry.split(":")[1]
    version = entry.split(":")[2].split("->")[1]

    dependencies.append(
        {
            "group": group,
            "artifact": artifact,
            "version": version
        }
    )

payload = {
        "project": project,
        "dependencies": dependencies
    }

print(json.dumps(payload))
open(f"{WORKING_DIR}/{PROCESS_DATA}", "w+").write(json.dumps(payload))